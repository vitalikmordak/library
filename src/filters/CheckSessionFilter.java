package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// close access to /pages/*
@WebFilter(filterName = "CheckSessionFilter", urlPatterns = "/pages/*")
public class CheckSessionFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(true);
        if (session == null || session.isNew()) {
            response.sendRedirect("../index.jsp");
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
