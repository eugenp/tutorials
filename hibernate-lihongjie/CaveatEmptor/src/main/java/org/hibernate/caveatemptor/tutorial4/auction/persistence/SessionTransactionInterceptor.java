package org.hibernate.caveatemptor.tutorial4.auction.persistence;

import org.jboss.aop.joinpoint.Invocation;
import org.hibernate.*;
import org.apache.commons.logging.*;

/**
 * Wraps a persistence context and a database transaction around a method call.
 * <p>
 * You can use this filter as-is for a thread-bound <tt>Session</tt>, set your
 * <tt>hibernate.transaction.factory_class</tt> accordingly to "thread". It could easily
 * be rewritten for programmatic JTA transaction demarcation with UserTransaction.
 * <p>
 * This is a more flexible alternative to a servlet filter, or most other kinds
 * of interceptors. You can apply it to anything you can define an AOP pointcut for,
 * which is, well, everything. Usually you would wrap this interceptor around a
 * service facade method that needs a persistence context, or, in other words, that
 * uses DAOs. This filter always propagates a transaction if one wrapped method would
 * call another wrapped method. The persistence context is also propagated (bound to the
 * thread internally). Note that you will have to enhance your bytecode to
 * apply this filter to arbitrary pointcuts. See build.xml and META-INF/jboss-aop.xml.
 * <p>
 * You could rewrite this interceptor easily for a session-per-conversation strategy. In
 * its given form it is best used to wrap a service method in a typical
 * session-per-request application.
 * <p>
 * Note that you should not use this interceptor out-of-the-box with enabled optimistic
 * concurrency control. Apply your own compensation logic for failed conversations, this
 * is totally dependent on your applications design.
 *
 * @see auction.web.filter.HibernateSessionRequestFilter
 * @see auction.web.filter.HibernateExtendedThreadFilter
 *
 * @author Christian Bauer
 */
public class SessionTransactionInterceptor
    implements org.jboss.aop.advice.Interceptor {

    private static Log log = LogFactory.getLog(SessionTransactionInterceptor.class);

    private static SessionFactory sf = HibernateUtil.getSessionFactory();

    public String getName() {
        return "SessionTransactionInterceptor";
    }

    public Object invoke(Invocation invocation) throws Throwable {

        try {
            boolean isActive = sf.getCurrentSession().getTransaction().isActive();
            if ( !isActive) {
               log.debug("Starting a database transaction");
               sf.getCurrentSession().beginTransaction();
            }

            log.debug("Invoking the aspectized service method");
            Object result = invocation.invokeNext();

            // Commit and cleanup
            if (!isActive) {
               log.debug("Committing the database transaction");
               sf.getCurrentSession().getTransaction().commit();
            }

            return result;

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
            try {
                log.warn("Trying to rollback database transaction after exception");
                sf.getCurrentSession().getTransaction().rollback();
            } catch (Throwable rbEx) {
                log.error("Could not rollback transaction after exception!", rbEx);
            }
            // Let others handle it... maybe another interceptor for exceptions?
            throw ex;
        }
    }

}
