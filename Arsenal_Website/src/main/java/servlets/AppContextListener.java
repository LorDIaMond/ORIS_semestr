package servlets;

import interfaces.repo.*;
import interfaces.serv.IProductService;
import interfaces.serv.IShopService;
import interfaces.serv.IUserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repositoryes.*;
import services.*;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Инициализация репозиториев
        IUserRepository userRepository = new UserRepository();
        IProductRepository productRepository = new ProductRepository();
        IFavoritesRepository favoritesRepository = new FavoritesRepository();
        IOrdersRepository ordersRepository = new OrdersRepository();
        ICategoryRepository categoryRepository = new CategoryRepository();
        IMatchRepository matchRepository = new MatchRepository();
        INewsRepository newsRepository = new NewsRepository();

        // Инициализация сервисов
        IUserService userService = new UserService(userRepository);
        IProductService productService = new ProductService(productRepository, userRepository);
        IShopService shopService = new ShopService(productRepository, categoryRepository);
        FavoritesService favoritesService = new FavoritesService(favoritesRepository);
        OrdersService ordersService = new OrdersService(ordersRepository);
        ProfileService profileService = new ProfileService(userRepository, favoritesRepository, ordersRepository);
        HomeService homeService = new HomeService(userRepository, matchRepository, newsRepository);
        AdminService adminService = new AdminService(productRepository, categoryRepository);

        context.setAttribute("userService", userService);
        context.setAttribute("productService", productService);
        context.setAttribute("shopService", shopService);
        context.setAttribute("favoritesService", favoritesService);
        context.setAttribute("ordersService", ordersService);
        context.setAttribute("profileService", profileService);
        context.setAttribute("homeService", homeService);
        context.setAttribute("adminService", adminService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Вроде бы все");
    }
}
