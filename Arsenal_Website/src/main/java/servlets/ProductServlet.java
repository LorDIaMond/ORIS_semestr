package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modules.Product;
import modules.User;
import services.ProductService;

import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.productService = (ProductService) context.getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        Long userId = (Long) request.getSession().getAttribute("userId");

        Product product = null;
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                long id = Long.parseLong(idParam);
                product = productService.getProductById(id);
            } catch (NumberFormatException ignored) {}
        }

        User user = productService.getUserById(userId);

        request.setAttribute("user", user);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }
}