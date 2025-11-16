package servlets;

import modules.Product;
import modules.Category;
import modules.User;
import repositoryes.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.FavoritesService;
import services.OrdersService;
import services.ShopService;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

    private ShopService shopService;
    private FavoritesService favoritesService;
    private OrdersService ordersService;
    private UserRepository userRepository;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        this.shopService = (ShopService) context.getAttribute("shopService");
        this.favoritesService = (FavoritesService) context.getAttribute("favoritesService");
        this.ordersService = (OrdersService) context.getAttribute("ordersService");
        this.userRepository = (UserRepository) context.getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String[] categoryIds = request.getParameterValues("categories");
        String[] priceRanges = request.getParameterValues("price");
        Long userId = (Long) request.getSession().getAttribute("userId");

        List<Product> products = shopService.getFilteredProducts(categoryIds, priceRanges);
        List<Category> categories = shopService.getAllCategories();

        Map<Long, Boolean> isFavorite = new HashMap<>();
        Map<Long, Boolean> cart = new HashMap<>();
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId);
            for (Product p : products) {
                isFavorite.put(p.getId(), favoritesService.isFavorite(userId, p.getId()));
                cart.put(p.getId(), ordersService.isInCart(userId, p.getId()));
            }
        }

        if ("true".equals(request.getParameter("ajax"))) {
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("products", products);
            request.setAttribute("isFavorite", isFavorite);
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/jsp/product-cards-AJAX.jsp").include(request, response);
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("isFavorite", isFavorite);
        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/jsp/shop.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String action = request.getParameter("action");
        String productIdParam = request.getParameter("productId");

        if (productIdParam == null || productIdParam.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Long productId = Long.parseLong(productIdParam);

            if ("addToFavorites".equals(action)) {
                boolean isFavorite = favoritesService.toggleFavorite(userId, productId);
                response.setContentType("application/json");
                response.getWriter().write("{\"favorite\":" + isFavorite + "}");

            } else if ("addToCart".equals(action)) {
                boolean inCart = ordersService.toggleCart(userId, productId);
                response.setContentType("application/json");
                response.getWriter().write("{\"inCart\":" + inCart + "}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}