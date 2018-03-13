package org.hibernate.caveatemptor.tutorial4.auction.persistence.audit;

import org.hibernate.*;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.*;

import auction.model.*;
import org.apache.commons.logging.*;

/**
 * Logs a simple audit trail for entity classes marked with <tt>Auditable</tt>.
 * <p>
 * When objects are updated or stored, this interceptor puts them in queues.
 * After flushing is complete, this interceptor iterates through the queues and
 * stores <tt>AuditLogRecord</tt> instances with the helper class <tt>AuditLog</tt>.
 * Because you can't access a <tt>Session</tt> inside its own interceptor, a
 * temporary second <tt>Session</tt> is opened on the same JDBC connection and
 * inside the same transaction, just to store the audit log objects.
 *
 * @see Auditable
 * @see AuditLog
 * @see AuditLogRecord
 * @author Christian Bauer
 */
public class AuditLogInterceptor extends EmptyInterceptor {

	private static Log log = LogFactory.getLog(AuditLogInterceptor.class);

	private Session session;
	private Long userId;

	private Set<Auditable> inserts = new HashSet<Auditable>();
	private Set<Auditable> updates = new HashSet<Auditable>();

	public void setSession(Session session) {
		this.session=session;
	}
	public void setUserId(Long userId) {
		this.userId=userId;
	}

	public boolean onSave(Object entity,
						 Serializable id,
						 Object[] state,
						 String[] propertyNames,
						 Type[] types)
			throws CallbackException {

		if (entity instanceof Auditable)
			inserts.add((Auditable)entity);

		return false;
	}

	public boolean onFlushDirty(Object entity,
								Serializable id,
								Object[] currentState,
								Object[] previousState,
								String[] propertyNames,
								Type[] types)
			throws CallbackException {
		if (entity instanceof Auditable)
			updates.add((Auditable)entity);

		return false;
	}

	public void postFlush(Iterator iterator) throws CallbackException {
		try {
            for (Auditable entity : inserts) {
                log.debug("Intercepted creation of : " + entity);
                AuditLog.logEvent("create",
                        entity,
                        userId,
                        session.connection());
            }
            for (Auditable entity : updates) {
                log.debug("Intercepted modification of : " + entity);
                AuditLog.logEvent("update",
                        entity,
                        userId,
                        session.connection());
            }
		} finally {
			inserts.clear();
			updates.clear();
		}
	}
}
