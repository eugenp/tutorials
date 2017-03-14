/**
 * ApplicationViewer is the class that starts the application
 * First it creates the session object and then creates the
 * criteria query.
 *
 *  @author  Sandeep Kumar
 *  @version 1.0
 *  @since   01/13/2017
 */

package com.baeldung.hibernate.criteria.view;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;

public class ApplicationView {
    
    @SuppressWarnings("unchecked")
    public boolean checkIfCriteriaTimeLower() {
        final Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        Transaction tx = null;

        // calculate the time taken by criteria
        final long startTimeCriteria = System.nanoTime();
        criteriaItem.select(rootItem).where(builder.like(rootItem.get("itemName"), "%item One%"));
        final List<Item> results = session.createQuery(criteriaItem).getResultList();
        final long endTimeCriteria = System.nanoTime();
        final long durationCriteria = (endTimeCriteria - startTimeCriteria) / 1000;

        // calculate the time taken by HQL
        final long startTimeHQL = System.nanoTime();
        tx = session.beginTransaction();
        final List<Item> items = session.createQuery("FROM Item where itemName like '%item One%'").getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.greaterThan(rootItem.get("itemPrice"), 1000));
        final List<Item> greaterThanItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.lessThan(rootItem.get("itemPrice"), 1000));
        final List<Item> lessThanItemsList = session.createQuery(criteriaItem).getResultList();
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

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.like(rootItem.get("itemName"), "%chair%"));
        final List<Item> likeItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.like(rootItem.get("itemName"), "%Chair%"));
        final List<Item> ilikeItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        // To get items having price more than 1000
        criteriaItem.select(rootItem).where(builder.between(rootItem.get("itemPrice"), 100, 200));
        final List<Item> betweenItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.isNull(rootItem.get("itemDescription")));
        final List<Item> nullItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.isNotNull(rootItem.get("itemDescription")));
        final List<Item> notNullItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.select(rootItem).where(builder.isNull(rootItem.get("itemDescription"))).where(builder.like(rootItem.get("itemName"), "chair%"));
        final List<Item> notNullItemsList = session.createQuery(criteriaItem).getResultList();
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        final Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        predicates.add(builder.greaterThan(rootItem.get("itemPrice"), 1000));
        predicates.add(builder.like(rootItem.get("itemName"), "Chair%"));
        Predicate andPredicate = builder.and(predicates.toArray(new Predicate[] {}));
        criteriaItem.select(rootItem).where(andPredicate);
        final List<Item> andItemsList = session.createQuery(criteriaItem).getResultList();
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
        List<Predicate> predicates = new ArrayList<Predicate>();
        final Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        predicates.add(builder.greaterThan(rootItem.get("itemPrice"), 1000));
        predicates.add(builder.like(rootItem.get("itemName"), "Chair%"));
        Predicate orPredicate = builder.or(predicates.toArray(new Predicate[] {}));
        criteriaItem.select(rootItem).where(orPredicate);
        final List<Item> orItemsList = session.createQuery(criteriaItem).getResultList();
        final String orItems[] = new String[orItemsList.size()];
        for (int i = 0; i < orItemsList.size(); i++) {
            orItems[i] = orItemsList.get(i).getItemName();
        }
        session.close();
        return orItems;
    }

    // Sorting example
    public String[] sortingCriteria() {
        List<Order> listOrders = new ArrayList<Order>();
        final Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Item> criteriaItem = builder.createQuery(Item.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        listOrders.add(builder.asc(rootItem.get("itemName")));
        listOrders.add(builder.desc(rootItem.get("itemPrice")));
        criteriaItem.orderBy(listOrders.toArray(new Order[] {}));
        final List<Item> sortedItemsList = session.createQuery(criteriaItem).getResultList();
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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaItem = builder.createQuery(Tuple.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.multiselect(builder.count(rootItem));
        final List<Tuple> itemProjected = session.createQuery(criteriaItem).getResultList();
        final Long projectedRowCount[] = new Long[1];
        projectedRowCount[0] = (long) itemProjected.get(0).get(0);
        session.close();
        return projectedRowCount;
    }

    // Set projections average of itemPrice
    public Double[] projectionAverage() {
        final Session session = HibernateUtil.getHibernateSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaItem = builder.createQuery(Tuple.class);
        Root<Item> rootItem = criteriaItem.from(Item.class);
        criteriaItem.multiselect(builder.avg(rootItem.get("itemPrice")));
        final List<Tuple> itemProjected = session.createQuery(criteriaItem).getResultList();
        Double avgItemPrice[] = new Double[1];
        avgItemPrice[0] = Double.valueOf(itemProjected.get(0).get(0).toString());
        session.close();
        return avgItemPrice;
    }

}
