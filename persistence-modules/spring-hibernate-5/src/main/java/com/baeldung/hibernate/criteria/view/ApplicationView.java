/**
 * ApplicationViewer is the class that starts the application
 * First it creates the session object and then creates the
 * criteria query.
 *
 *  @author  Pritam Banerjee
 *  @version 1.0
 *  @since   07/20/2016
 */

package com.baeldung.hibernate.criteria.view;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;

public class ApplicationView {

    // default Constructor
    public ApplicationView() {

    }

    public boolean checkIfCriteriaTimeLower() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);

        // calculate the time taken by criteria
        final long startTimeCriteria = System.nanoTime();
        cr.select(root)
            .where(cb.like(root.get("itemName"), "%item One%"));
        // .add(Restrictions.like("itemName", "%item One%"));
        Query<Item> query = session.createQuery(cr);

        final List<Item> results = query.getResultList();
        final long endTimeCriteria = System.nanoTime();
        final long durationCriteria = (endTimeCriteria - startTimeCriteria) / 1000;

        // calculate the time taken by HQL
        final long startTimeHQL = System.nanoTime();
        session.beginTransaction();
        final List<Item> items = session.createQuery("FROM Item where itemName like '%item One%'")
            .list();
        final long endTimeHQL = System.nanoTime();
        final long durationHQL = (endTimeHQL - startTimeHQL) / 1000;

        if (durationCriteria > durationHQL) {
            return false;
        } else {
            return true;
        }
    }

    // To get items having price more than 1000
    public String[] greaterThanCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.gt(root.get("itemPrice"), 1000));
        // cr.add(Restrictions.gt("itemPrice", 1000));
        Query<Item> query = session.createQuery(cr);
        final List<Item> greaterThanItemsList = query.getResultList();
        final String greaterThanItems[] = new String[greaterThanItemsList.size()];
        for (int i = 0; i < greaterThanItemsList.size(); i++) {
            greaterThanItems[i] = greaterThanItemsList.get(i)
                .getItemName();
        }
        session.close();
        return greaterThanItems;
    }

    // To get items having price less than 1000
    public String[] lessThanCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.lt(root.get("itemPrice"), 1000));
        // cr.add(Restrictions.lt("itemPrice", 1000));
        Query<Item> query = session.createQuery(cr);
        final List<Item> lessThanItemsList = query.getResultList();
        final String lessThanItems[] = new String[lessThanItemsList.size()];
        for (int i = 0; i < lessThanItemsList.size(); i++) {
            lessThanItems[i] = lessThanItemsList.get(i)
                .getItemName();
        }
        session.close();
        return lessThanItems;
    }

    // To get items whose Name start with Chair
    public String[] likeCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.like(root.get("itemName"), "%chair%"));
        // cr.add(Restrictions.like("itemName", "%chair%"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> likeItemsList = query.getResultList();
        final String likeItems[] = new String[likeItemsList.size()];
        for (int i = 0; i < likeItemsList.size(); i++) {
            likeItems[i] = likeItemsList.get(i)
                .getItemName();
        }
        session.close();
        return likeItems;
    }

    // Case sensitive search
    public String[] likeCaseCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.like(cb.lower(root.get("itemName")), "%chair%"));
        // cr.add(Restrictions.ilike("itemName", "%Chair%"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> ilikeItemsList = query.getResultList();
        final String ilikeItems[] = new String[ilikeItemsList.size()];
        for (int i = 0; i < ilikeItemsList.size(); i++) {
            ilikeItems[i] = ilikeItemsList.get(i)
                .getItemName();
        }
        session.close();
        return ilikeItems;
    }

    // To get records having itemPrice in between 100 and 200
    public String[] betweenCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.between(root.get("itemPrice"), 100, 200));
        // cr.add(Restrictions.between("itemPrice", 100, 200));
        Query<Item> query = session.createQuery(cr);
        final List<Item> betweenItemsList = query.getResultList();
        final String betweenItems[] = new String[betweenItemsList.size()];
        for (int i = 0; i < betweenItemsList.size(); i++) {
            betweenItems[i] = betweenItemsList.get(i)
                .getItemName();
        }
        session.close();
        return betweenItems;
    }

    // To get records having itemName in 'Skate Board', 'Paint' and 'Glue'
    public String[] inCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(root.get("itemName").in("Skate Board", "Paint", "Glue"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> inItemsList = query.getResultList();
        final String inItems[] = new String[inItemsList.size()];
        for (int i = 0; i < inItemsList.size(); i++) {
            inItems[i] = inItemsList.get(i)
                    .getItemName();
        }
        session.close();
        return inItems;
    }

    // To check if the given property is null
    public String[] nullCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.isNull(root.get("itemDescription")));
        // cr.add(Restrictions.isNull("itemDescription"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> nullItemsList = query.getResultList();
        final String nullDescItems[] = new String[nullItemsList.size()];
        for (int i = 0; i < nullItemsList.size(); i++) {
            nullDescItems[i] = nullItemsList.get(i)
                .getItemName();
        }
        session.close();
        return nullDescItems;
    }

    // To check if the given property is not null
    public String[] notNullCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root)
            .where(cb.isNotNull(root.get("itemDescription")));
        // cr.add(Restrictions.isNotNull("itemDescription"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> notNullItemsList = query.getResultList();
        final String notNullDescItems[] = new String[notNullItemsList.size()];
        for (int i = 0; i < notNullItemsList.size(); i++) {
            notNullDescItems[i] = notNullItemsList.get(i)
                .getItemName();
        }
        session.close();
        return notNullDescItems;
    }

    // Adding more than one expression in one cr
    public String[] twoCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.isNull(root.get("itemDescription"));
        predicates[1] = cb.like(root.get("itemName"), "chair%");
        cr.select(root)
            .where(predicates);
        // cr.add(Restrictions.isNull("itemDescription"));
        // cr.add(Restrictions.like("itemName", "chair%"));
        Query<Item> query = session.createQuery(cr);
        final List<Item> notNullItemsList = query.getResultList();
        final String notNullDescItems[] = new String[notNullItemsList.size()];
        for (int i = 0; i < notNullItemsList.size(); i++) {
            notNullDescItems[i] = notNullItemsList.get(i)
                .getItemName();
        }
        session.close();
        return notNullDescItems;
    }

    // To get items matching with the above defined conditions joined
    // with Logical AND
    public String[] andLogicalCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        Predicate greaterThanPrice = cb.gt(root.get("itemPrice"), 1000);
        Predicate chairItems = cb.like(root.get("itemName"), "Chair%");
        cr.select(root)
            .where(cb.and(greaterThanPrice, chairItems));
        // final Criterion greaterThanPrice = Restrictions.gt("itemPrice", 1000);
        // final Criterion chairItems = Restrictions.like("itemName", "Chair%");
        // final LogicalExpression andExample = Restrictions.and(greaterThanPrice, chairItems);
        // cr.add(andExample);
        Query<Item> query = session.createQuery(cr);
        final List<Item> andItemsList = query.getResultList();
        final String andItems[] = new String[andItemsList.size()];
        for (int i = 0; i < andItemsList.size(); i++) {
            andItems[i] = andItemsList.get(i)
                .getItemName();
        }
        session.close();
        return andItems;
    }

    // To get items matching with the above defined conditions joined
    // with Logical OR
    public String[] orLogicalCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        Predicate greaterThanPrice = cb.gt(root.get("itemPrice"), 1000);
        Predicate chairItems = cb.like(root.get("itemName"), "Chair%");
        cr.select(root)
            .where(cb.or(greaterThanPrice, chairItems));
        Query<Item> query = session.createQuery(cr);
        final List<Item> orItemsList = query.getResultList();
        final String orItems[] = new String[orItemsList.size()];
        for (int i = 0; i < orItemsList.size(); i++) {
            orItems[i] = orItemsList.get(i)
                .getItemName();
        }
        session.close();
        return orItems;
    }

    // Sorting example
    public String[] sortingCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Item> cr = cb.createQuery(Item.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(root);
        cr.orderBy(cb.asc(root.get("itemName")), cb.desc(root.get("itemPrice")));
        // cr.addOrder(Order.asc("itemName"));
        // cr.addOrder(Order.desc("itemPrice")).list();
        Query<Item> query = session.createQuery(cr);
        final List<Item> sortedItemsList = query.getResultList();
        final String sortedItems[] = new String[sortedItemsList.size()];
        for (int i = 0; i < sortedItemsList.size(); i++) {
            sortedItems[i] = sortedItemsList.get(i)
                .getItemName();
        }
        session.close();
        return sortedItems;
    }

    // Set projections Row Count
    public Long[] projectionRowCount() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Long> cr = cb.createQuery(Long.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(cb.count(root));
        Query<Long> query = session.createQuery(cr);
        final List<Long> itemProjected = query.getResultList();
        // session.createCriteria(Item.class).setProjection(Projections.rowCount()).list();
        final Long projectedRowCount[] = new Long[itemProjected.size()];
        for (int i = 0; i < itemProjected.size(); i++) {
            projectedRowCount[i] = itemProjected.get(i);
        }
        session.close();
        return projectedRowCount;
    }

    // Set projections average of itemPrice
    public Double[] projectionAverage() {
        final Session session = HibernateUtil.getHibernateSession();
        final CriteriaBuilder cb = session.getCriteriaBuilder();
        final CriteriaQuery<Double> cr = cb.createQuery(Double.class);
        final Root<Item> root = cr.from(Item.class);
        cr.select(cb.avg(root.get("itemPrice")));
        Query<Double> query = session.createQuery(cr);
        final List avgItemPriceList = query.getResultList();
        // session.createCriteria(Item.class).setProjection(Projections.projectionList().add(Projections.avg("itemPrice"))).list();

        final Double avgItemPrice[] = new Double[avgItemPriceList.size()];
        for (int i = 0; i < avgItemPriceList.size(); i++) {
            avgItemPrice[i] = (Double) avgItemPriceList.get(i);
        }
        session.close();
        return avgItemPrice;
    }

}
