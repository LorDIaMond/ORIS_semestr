package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modules.Category;
import modules.Product;
import modules.User;
import services.AdminService;
import services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/edit")
public class EditProductServlet extends HttpServlet {

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

        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = userService.getUserById(userId);

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            Product product = adminService.getProductById(id);
            List<Category> categories = adminService.getAllCategories();

            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/admin");
                return;
            }

            request.setAttribute("user", user);
            request.setAttribute("product", product);
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/jsp/edit-product.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Проверки авторизации НЕ НУЖНЫ — их делает WebFilter
        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin");
            return;
        }

        try {
            long id = Long.parseLong(idParam);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String categoryIdStr = request.getParameter("categoryId");

            if (name == null || name.trim().isEmpty() || priceStr == null || categoryIdStr == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            double price = Double.parseDouble(priceStr);
            long categoryId = Long.parseLong(categoryIdStr);

            adminService.updateProduct(id, name, description, price, categoryId);
            response.sendRedirect(request.getContextPath() + "/admin");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin");
        }
    }
}