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
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        this.userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Map<String, String> errors = userService.validateAndRegister(name, email, password);

        if (errors != null) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        User user = userService.findByEmail(email);
        request.getSession().setAttribute("userId", user.getId());
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}