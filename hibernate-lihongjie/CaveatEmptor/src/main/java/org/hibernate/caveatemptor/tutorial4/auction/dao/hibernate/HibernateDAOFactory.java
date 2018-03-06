package org.hibernate.caveatemptor.tutorial4.auction.dao.hibernate;

import auction.dao.*;
import auction.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Returns Hibernate-specific instances of DAOs.
 * <p/>
 * If for a particular DAO there is no additional non-CRUD functionality, we use
 * a nested static class to implement the interface in a generic way. This allows clean
 * refactoring later on, should the interface implement business data access
 * methods at some later time. Then, we would externalize the implementation into
 * its own first-level class.
 *
 * @author Christian Bauer
 */
public class HibernateDAOFactory extends DAOFactory {

    private static Log log = LogFactory.getLog(HibernateDAOFactory.class);

    public ItemDAO getItemDAO() {
        return (ItemDAO)instantiateDAO(ItemDAOHibernate.class);
    }

    public CategoryDAO getCategoryDAO() {
        return (CategoryDAO)instantiateDAO(CategoryDAOHibernate.class);
    }

    public CommentDAO getCommentDAO() {
        return (CommentDAO)instantiateDAO(CommentDAOHibernate.class);
    }

    public UserDAO getUserDAO() {
        return (UserDAO)instantiateDAO(UserDAOHibernate.class);
    }

    public CategorizedItemDAO getCategorizedItemDAO() {
        return (CategorizedItemDAO)instantiateDAO(CategorizedItemDAOHibernate.class);
    }

    public BillingDetailsDAO getBillingDetailsDAO() {
        return (BillingDetailsDAO)instantiateDAO(BillingDetailsDAOHibernate.class);
    }

    public ShipmentDAO getShipmentDAO() {
        return (ShipmentDAO)instantiateDAO(ShipmentDAOHibernate.class);
    }

    private GenericHibernateDAO instantiateDAO(Class daoClass) {
        try {
            log.debug("Instantiating DAO: " + daoClass);
            return (GenericHibernateDAO)daoClass.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }

    // Inline concrete DAO implementations with no business-related data access methods.
    // If we use public static nested classes, we can centralize all of them in one source file.

    public static class CategorizedItemDAOHibernate
            extends GenericHibernateDAO<CategorizedItem, CategorizedItem.Id>
            implements CategorizedItemDAO {}

    public static class CommentDAOHibernate
            extends GenericHibernateDAO<Comment, Long>
            implements CommentDAO {}

    public static class ShipmentDAOHibernate
            extends GenericHibernateDAO<Shipment, Long>
            implements ShipmentDAO {}

}
