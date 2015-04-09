package org.baeldung.SpringDataAuditDemo.dao.repos;

import org.baeldung.SpringDataAuditDemo.dao.GenericDao;
import org.baeldung.SpringDataAuditDemo.model.Order;

/**
 * Represents dao interface for {@link org.baeldung.SpringDataAuditDemo.model.Order} entity. 
 */
public interface OrderDao extends GenericDao<Order, Long> {
}
