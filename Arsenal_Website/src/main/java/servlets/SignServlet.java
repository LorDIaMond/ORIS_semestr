package servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modules.User;
import services.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/sign")
public class SignServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/jsp/sign.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.authenticate(email, password);

        if (user == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("email", "Email or password was entered incorrectly.");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/jsp/sign.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("userId", user.getId());
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
