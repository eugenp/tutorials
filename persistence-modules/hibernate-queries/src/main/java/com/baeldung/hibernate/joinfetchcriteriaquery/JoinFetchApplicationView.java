package com.baeldung.hibernate.joinfetchcriteriaquery;

import com.baeldung.hibernate.joinfetchcriteriaquery.model.League;
import com.baeldung.hibernate.joinfetchcriteriaquery.model.Player;

import com.baeldung.hibernate.joinfetchcriteriaquery.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class JoinFetchApplicationView {

    public static List<Player> joinFetch() {
        final SessionFactory sessionFactory = HibernateUtil.getHibernateSessionFactory();
        Session session = sessionFactory.openSession();

        persistData(session);

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> query = cb.createQuery(Player.class);
        Root<Player> root = query.from(Player.class);
        root.fetch("league", JoinType.LEFT);
        query.where(cb.equal(root.get("league")
          .get("name"), "Premier League"));
        List<Player> playerList = session.createQuery(query)
          .getResultList();

        return playerList;
    }

    private static void persistData(Session session) {
        Transaction transaction = session.beginTransaction();
        League premier = session.find(League.class, 6);
        if (premier == null) {
            premier = new League(6, "Premier League");
            session.persist(premier);
        }
        Player saka = new Player(90, "Saka", "Arsenal", 22, premier);
        Player rice = new Player(91, "Mike", "Arsenal", 30, premier);
        session.persist(saka);
        session.persist(rice);
        transaction.commit();

    }
}
