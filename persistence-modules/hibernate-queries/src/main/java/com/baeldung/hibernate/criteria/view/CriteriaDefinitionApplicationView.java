package com.baeldung.hibernate.criteria.view;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.CriteriaDefinition;
import org.hibernate.query.criteria.JpaRoot;

import com.baeldung.hibernate.criteria.model.Item;
import com.baeldung.hibernate.criteria.util.HibernateUtil;

public class CriteriaDefinitionApplicationView {

    public CriteriaDefinitionApplicationView() {

    }

    public String[] greaterThanCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();

        CriteriaDefinition<Item> query = new CriteriaDefinition<>(sessionFactory, Item.class) {
            {
                JpaRoot<Item> message = from(Item.class);
                where(gt(message.get("itemPrice"), 1000));
            }
        };

        List<Item> items = session.createSelectionQuery(query).list();

        final String greaterThanItems[] = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            greaterThanItems[i] = items.get(i).getItemName();
        }

        session.close();

        return greaterThanItems;
    }

    public String[] lessThanCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();

        CriteriaDefinition<Item> query = new CriteriaDefinition<>(sessionFactory, Item.class) {
            {
                JpaRoot<Item> message = from(Item.class);
                where(lt(message.get("itemPrice"), 1000));
            }
        };

        List<Item> items = session.createSelectionQuery(query).list();
        final String lessThanItems[] = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            lessThanItems[i] = items.get(i).getItemName();
        }

        session.close();

        return lessThanItems;
    }

    public String[] likeCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();

        CriteriaDefinition<Item> query = new CriteriaDefinition<>(sessionFactory, Item.class) {
            {
                JpaRoot<Item> item = from(Item.class);
                where(like(item.get("itemName"), "%chair%"));
            }
        };

        List<Item> items = session.createSelectionQuery(query).list();

        final String likeItems[] = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            likeItems[i] = items.get(i).getItemName();
        }
        session.close();
        return likeItems;
    }

    public String[] likeCaseCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();

        CriteriaDefinition<Item> query = new CriteriaDefinition<>(sessionFactory, Item.class) {
            {
                JpaRoot<Item> item = from(Item.class);
                where(like(lower(item.get("itemName")), "%chair%"));
            }
        };

        List<Item> items = session.createSelectionQuery(query).list();

        final String likeItems[] = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            likeItems[i] = items.get(i).getItemName();
        }
        session.close();
        return likeItems;
    }

    public String[] betweenCriteria() {
        final Session session = HibernateUtil.getHibernateSession();
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();

        CriteriaDefinition<Item> query = new CriteriaDefinition<>(sessionFactory, Item.class) {
            {
                JpaRoot<Item> item = from(Item.class);
                where(between(item.get("itemPrice"), 100, 200));
            }
        };

        List<Item> items = session.createSelectionQuery(query).list();

        final String betweenItems[] = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            betweenItems[i] = items.get(i).getItemName();
        }
        session.close();
        return betweenItems;
    }
}
