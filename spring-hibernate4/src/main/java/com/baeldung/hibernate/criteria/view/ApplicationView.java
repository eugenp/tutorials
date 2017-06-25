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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;

public class ApplicationView {

    // default Constructor
    public ApplicationView() {

    }

    public boolean checkIfCriteriaTimeLower() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        Transaction tx = null;

        // calculate the time taken by criteria
        final long startTimeCriteria = System.nanoTime();
        cr.add(Restrictions.like("itemName", "%item One%"));
        final List results = cr.list();
        final long endTimeCriteria = System.nanoTime();
        final long durationCriteria = (endTimeCriteria - startTimeCriteria) / 1000;

        // calculate the time taken by HQL
        final long startTimeHQL = System.nanoTime();
        tx = session.beginTransaction();
        final List items = session.createQuery("FROM Item where itemName like '%item One%'").list();
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
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.gt("itemPrice", 1000));
        final List<Item> greaterThanItemsList = cr.list();
        final String greaterThanItems[] = new String[greaterThanItemsList.size()];
        for (int i = 0; i < greaterThanItemsList.size(); i++) {
            greaterThanItems[i] = greaterThanItemsList.get(i).getItemName();
        }
        session.close();
        return greaterThanItems;
    }

    // To get items having price less than 1000
    public String[] lessThanCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.lt("itemPrice", 1000));
        final List<Item> lessThanItemsList = cr.list();
        final String lessThanItems[] = new String[lessThanItemsList.size()];
        for (int i = 0; i < lessThanItemsList.size(); i++) {
            lessThanItems[i] = lessThanItemsList.get(i).getItemName();
        }
        session.close();
        return lessThanItems;
    }

    // To get items whose Name start with Chair
    public String[] likeCriteria() {
        final Session session = HibernateUtil.getHibernateSession();

        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.like("itemName", "%chair%"));
        final List<Item> likeItemsList = cr.list();
        final String likeItems[] = new String[likeItemsList.size()];
        for (int i = 0; i < likeItemsList.size(); i++) {
            likeItems[i] = likeItemsList.get(i).getItemName();
        }
        session.close();
        return likeItems;
    }

    // Case sensitive search
    public String[] likeCaseCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.ilike("itemName", "%Chair%"));
        final List<Item> ilikeItemsList = cr.list();
        final String ilikeItems[] = new String[ilikeItemsList.size()];
        for (int i = 0; i < ilikeItemsList.size(); i++) {
            ilikeItems[i] = ilikeItemsList.get(i).getItemName();
        }
        session.close();
        return ilikeItems;
    }

    // To get records having itemPrice in between 100 and 200
    public String[] betweenCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        // To get items having price more than 1000
        cr.add(Restrictions.between("itemPrice", 100, 200));
        final List<Item> betweenItemsList = cr.list();
        final String betweenItems[] = new String[betweenItemsList.size()];
        for (int i = 0; i < betweenItemsList.size(); i++) {
            betweenItems[i] = betweenItemsList.get(i).getItemName();
        }
        session.close();
        return betweenItems;
    }

    // To check if the given property is null
    public String[] nullCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.isNull("itemDescription"));
        final List<Item> nullItemsList = cr.list();
        final String nullDescItems[] = new String[nullItemsList.size()];
        for (int i = 0; i < nullItemsList.size(); i++) {
            nullDescItems[i] = nullItemsList.get(i).getItemName();
        }
        session.close();
        return nullDescItems;
    }

    // To check if the given property is not null
    public String[] notNullCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.isNotNull("itemDescription"));
        final List<Item> notNullItemsList = cr.list();
        final String notNullDescItems[] = new String[notNullItemsList.size()];
        for (int i = 0; i < notNullItemsList.size(); i++) {
            notNullDescItems[i] = notNullItemsList.get(i).getItemName();
        }
        session.close();
        return notNullDescItems;
    }

    // Adding more than one expression in one cr
    public String[] twoCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.add(Restrictions.isNull("itemDescription"));
        cr.add(Restrictions.like("itemName", "chair%"));
        final List<Item> notNullItemsList = cr.list();
        final String notNullDescItems[] = new String[notNullItemsList.size()];
        for (int i = 0; i < notNullItemsList.size(); i++) {
            notNullDescItems[i] = notNullItemsList.get(i).getItemName();
        }
        session.close();
        return notNullDescItems;
    }

    // To get items matching with the above defined conditions joined
    // with Logical AND
    public String[] andLogicalCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        final Criterion greaterThanPrice = Restrictions.gt("itemPrice", 1000);
        final Criterion chairItems = Restrictions.like("itemName", "Chair%");
        final LogicalExpression andExample = Restrictions.and(greaterThanPrice, chairItems);
        cr.add(andExample);
        final List<Item> andItemsList = cr.list();
        final String andItems[] = new String[andItemsList.size()];
        for (int i = 0; i < andItemsList.size(); i++) {
            andItems[i] = andItemsList.get(i).getItemName();
        }
        session.close();
        return andItems;
    }

    // To get items matching with the above defined conditions joined
    // with Logical OR
    public String[] orLogicalCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        final Criterion greaterThanPrice = Restrictions.gt("itemPrice", 1000);
        final Criterion chairItems = Restrictions.like("itemName", "Chair%");
        final LogicalExpression orExample = Restrictions.or(greaterThanPrice, chairItems);
        cr.add(orExample);
        final List<Item> orItemsList = cr.list();
        final String orItems[] = new String[orItemsList.size()];
        for (int i = 0; i < orItemsList.size(); i++) {
            orItems[i] = orItemsList.get(i).getItemName();
        }
        session.close();
        return orItems;
    }

    // Sorting example
    public String[] sortingCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final Criteria cr = session.createCriteria(Item.class);
        cr.addOrder(Order.asc("itemName"));
        cr.addOrder(Order.desc("itemPrice")).list();
        final List<Item> sortedItemsList = cr.list();
        final String sortedItems[] = new String[sortedItemsList.size()];
        for (int i = 0; i < sortedItemsList.size(); i++) {
            sortedItems[i] = sortedItemsList.get(i).getItemName();
        }
        session.close();
        return sortedItems;
    }

    // Set projections Row Count
    public Long[] projectionRowCount() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Long> itemProjected = session.createCriteria(Item.class).setProjection(Projections.rowCount()).list();
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
        final List avgItemPriceList = session.createCriteria(Item.class).setProjection(Projections.projectionList().add(Projections.avg("itemPrice"))).list();

        final Double avgItemPrice[] = new Double[avgItemPriceList.size()];
        for (int i = 0; i < avgItemPriceList.size(); i++) {
            avgItemPrice[i] = (Double) avgItemPriceList.get(i);
        }
        session.close();
        return avgItemPrice;
    }

}
