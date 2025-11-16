package servlets;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import modules.Category;
import modules.Product;
import modules.User;
import services.AdminService;
import services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class AdminPanelServlet extends HttpServlet {

    private AdminService adminService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.adminService = (AdminService) context.getAttribute("adminService");
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Проверки НЕ НУЖНЫ — их делает WebFilter
        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = userService.getUserById(userId); // ← используем сервис

        List<Product> products = adminService.getAllProducts();
        List<Category> categories = adminService.getAllCategories();

        request.setAttribute("user", user);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/jsp/admin-panel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Проверки НЕ НУЖНЫ — их делает WebFilter
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Валидация
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("categoryId");

            if (name == null || name.trim().isEmpty() || priceStr == null || categoryIdStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields");
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                long categoryId = Long.parseLong(categoryIdStr);
                Part imagePart = request.getPart("image");

                adminService.createProduct(name, description, price, categoryId, imagePart, getServletContext().getRealPath("/"));

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
                return;
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return;
            }

        } else if ("delete".equals(action)) {
            String idStr = request.getParameter("id");
            try {
                long id = Long.parseLong(idStr);
                adminService.deleteProduct(id);
            } catch (NumberFormatException ignored) {}
        }

        response.sendRedirect(request.getContextPath() + "/admin");
    }
}