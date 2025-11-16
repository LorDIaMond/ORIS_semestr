package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modules.Product;
import modules.User;
import repositoryes.FavoritesRepository;
import repositoryes.OrdersRepository;
import repositoryes.UserRepository;
import services.ProfileService;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ProfileService profileService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.profileService = (ProfileService) context.getAttribute("profileService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/sign");
            return;
        }

        User user = profileService.getUserById(userId);
        if (user == null) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/sign");
            return;
        }

        List<Product> favoriteProducts = profileService.getFavoriteProducts(userId);
        List<Product> orders = profileService.getOrderedProducts(userId);

        request.setAttribute("user", user);
        request.setAttribute("favoriteProducts", favoriteProducts);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/jsp/profile.jsp").forward(request, response);
    }
}
