package org.hibernate.caveatemptor.tutorial4.auction.web.filter;

import org.apache.commons.logging.*;
import org.hibernate.*;
import auction.persistence.HibernateUtil;

import javax.servlet.*;
import javax.servlet.Filter;
import java.io.IOException;

/**
 * Filter that manages a Hibernate Session for a request.
 * <p>
 * This filter should be used if your <tt>hibernate.current_session_context_class</tt>
 * configuration is set to <tt>thread</tt> and you are not using JTA or CMT.
 * <p>
 * With JTA you'd replace transaction demarcation with calls to the <tt>UserTransaction</tt> API.
 * With CMT you would remove transaction demarcation code from this filter.
 * <p>
 * An alternative, more flexible solution is <tt>SessionTransactionInterceptor</tt>
 * that can be applied to any pointcut with JBoss AOP.
 * <p>
 * Note that you should not use this interceptor out-of-the-box with enabled optimistic
 * concurrency control. Apply your own compensation logic for failed conversations, this
 * is totally dependent on your applications design.
 *
 * @see auction.persistence.SessionTransactionInterceptor
 *
 * @author Christian Bauer
 */
public class HibernateSessionRequestFilter implements Filter {

    private static Log log = LogFactory.getLog(HibernateSessionRequestFilter.class);

    private SessionFactory sf;

    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        try {
            log.debug("Starting a database transaction");
            sf.getCurrentSession().beginTransaction();

            // Call the next filter (continue request processing)
            chain.doFilter(request, response);

            // Commit and cleanup
            log.debug("Committing the database transaction");
            sf.getCurrentSession().getTransaction().commit();

        } catch (StaleObjectStateException staleEx) {
            log.error("This interceptor does not implement optimistic concurrency control!");
            log.error("Your application will not work until you add compensation actions!");
            // Rollback, close everything, possibly compensate for any permanent changes
            // during the conversation, and finally restart business conversation. Maybe
            // give the user of the application a chance to merge some of his work with
            // fresh data... what you do here depends on your applications design.
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                if (sf.getCurrentSession().getTransaction().isActive()) {
                    log.debug("Trying to rollback database transaction after exception");
                    sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
                log.error("Could not rollback transaction after exception!", rbEx);
            }

            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Initializing filter...");
        log.debug("Obtaining SessionFactory from HibernateUtil");
        sf = HibernateUtil.getSessionFactory();
    }

    public void destroy() {}

}