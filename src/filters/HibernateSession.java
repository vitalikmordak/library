package filters;

import entities.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

// Filter before each request to the DB, the transaction will begin and then commit.
@WebFilter(filterName = "HibernateSession", urlPatterns = "/*")
public class HibernateSession implements Filter {
    private SessionFactory sessionFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            sessionFactory.getCurrentSession().beginTransaction();
            filterChain.doFilter(servletRequest, servletResponse);
            sessionFactory.getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (sessionFactory.getCurrentSession().getTransaction().isActive())
                sessionFactory.getCurrentSession().getTransaction().rollback();
        }
    }

    @Override
    public void destroy() {

    }
}
