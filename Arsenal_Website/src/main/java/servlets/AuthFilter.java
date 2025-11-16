package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repositoryes.UserRepository;

import java.io.IOException;

@WebFilter({"/profile", "/admin/*"})
public class AuthFilter implements Filter {

    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) {
        this.userRepository = new UserRepository();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Long userId = (Long) httpRequest.getSession().getAttribute("userId");
        if (userId == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/sign");
            return;
        }

        // Проверка админки
        if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/admin")) {
            var user = userRepository.findById(userId);
            if (user == null || !user.getIsAdmin()) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
