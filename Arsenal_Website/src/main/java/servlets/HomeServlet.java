package servlets;

import jakarta.servlet.ServletContext;
import modules.Match;
import modules.News;
import modules.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.HomeService;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    private HomeService homeService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.homeService = (HomeService) context.getAttribute("homeService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");
        User user = homeService.getUserById(userId);
        Match match = homeService.getCurrentMatch();
        News news = homeService.getLatestNews();

        request.setAttribute("user", user);
        request.setAttribute("match", match);
        request.setAttribute("news", news);
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}