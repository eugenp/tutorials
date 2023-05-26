package com.baeldung.countrows.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.countrows.entity.Account;
import com.baeldung.countrows.entity.Permission;
import com.baeldung.countrows.repository.AccountRepository;
import com.baeldung.countrows.repository.PermissionRepository;

@Service
public class AccountStatsLogic {
    @Autowired
    private AccountRepository accountRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PermissionRepository permissionRepository;

    public long getAccountCount() {
        return accountRepository.count();
    }

    public long getAccountCountByUsername(String username) {
        return accountRepository.countByUsername(username);
    }

    public long getAccountCountByPermission(Permission permission) {
        return accountRepository.countByPermission(permission);
    }

    public long getAccountCountByPermissionAndCreatedOn(Permission permission, Date date) throws ParseException {
        return accountRepository.countByPermissionAndCreatedOnGreaterThan(permission, new Timestamp(date.getTime()));
    }

    public long getAccountsUsingCQ() throws ParseException {
        // creating criteria builder and query
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Account> accountRoot = criteriaQuery.from(Account.class);

        // select query
        criteriaQuery.select(builder.count(accountRoot));

        // execute and get the result
        return entityManager.createQuery(criteriaQuery)
            .getSingleResult();
    }

    public long getAccountsByPermissionUsingCQ(Permission permission) throws ParseException {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Account> accountRoot = criteriaQuery.from(Account.class);

        List<Predicate> predicateList = new ArrayList<>(); // list of predicates that will go in where clause
        predicateList.add(builder.equal(accountRoot.get("permission"), permission));

        criteriaQuery.select(builder.count(accountRoot))
            .where(builder.and(predicateList.toArray(new Predicate[0])));

        return entityManager.createQuery(criteriaQuery)
            .getSingleResult();
    }

    public long getAccountsByPermissionAndCreateOnUsingCQ(Permission permission, Date date) throws ParseException {
        // creating criteria builder and query
        CriteriaBuilder builder = entityManager.getCriteriaBuilder(); // create builder
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);// query instance
        Root<Account> accountRoot = criteriaQuery.from(Account.class); // root instance

        // list of predicates that will go in where clause
        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(builder.equal(accountRoot.get("permission"), permission));
        predicateList.add(builder.greaterThan(accountRoot.get("createdOn"), new Timestamp(date.getTime())));

        // select query
        criteriaQuery.select(builder.count(accountRoot))
            .where(builder.and(predicateList.toArray(new Predicate[0])));

        // execute and get the result
        return entityManager.createQuery(criteriaQuery)
            .getSingleResult();
    }

    public long getAccountsUsingJPQL() throws ParseException {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Account a");
        return (long) query.getSingleResult();
    }

    public long getAccountsByPermissionUsingJPQL(Permission permission) throws ParseException {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Account a WHERE a.permission = ?1");
        query.setParameter(1, permission);
        return (long) query.getSingleResult();
    }

    public long getAccountsByPermissionAndCreatedOnUsingJPQL(Permission permission, Date date) throws ParseException {
        Query query = entityManager.createQuery("SELECT COUNT(*) FROM Account a WHERE a.permission = ?1 and a.createdOn > ?2");
        query.setParameter(1, permission);
        query.setParameter(2, new Timestamp(date.getTime()));
        return (long) query.getSingleResult();
    }
}