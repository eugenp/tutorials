/**
 * 
 */
package com.baeldung.examples.security.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class AccountDAO {

    private final DataSource dataSource;
    private final EntityManager em;

    public AccountDAO(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> unsafeFindAccountsByCustomerId(String customerId) {

        String sql = "select " + "customer_id,acc_number,branch_id,balance from Accounts where customer_id = '" + customerId + "'";

        try (Connection c = dataSource.getConnection();
            ResultSet rs = c.createStatement()
                .executeQuery(sql)) {
            List<AccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO acc = AccountDTO.builder()
                    .customerId(rs.getString("customer_id"))
                    .branchId(rs.getString("branch_id"))
                    .accNumber(rs.getString("acc_number"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();

                accounts.add(acc);
            }

            return accounts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id - JPA version
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> unsafeJpaFindAccountsByCustomerId(String customerId) {
        String jql = "from Account where customerId = '" + customerId + "'";
        TypedQuery<Account> q = em.createQuery(jql, Account.class);
        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeFindAccountsByCustomerId(String customerId) {

        String sql = "select customer_id, branch_id,acc_number,balance from Accounts where customer_id = ?";

        try (Connection c = dataSource.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, customerId);
            ResultSet rs = p.executeQuery();
            List<AccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO acc = AccountDTO.builder()
                    .customerId(rs.getString("customerId"))
                    .branchId(rs.getString("branch_id"))
                    .accNumber(rs.getString("acc_number"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();

                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id - JPA version
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeJpaFindAccountsByCustomerId(String customerId) {

        String jql = "from Account where customerId = :customerId";
        TypedQuery<Account> q = em.createQuery(jql, Account.class)
            .setParameter("customerId", customerId);

        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
    }

    /**
     * Return all accounts owned by a given customer,given his/her external id - JPA version
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeJpaCriteriaFindAccountsByCustomerId(String customerId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> root = cq.from(Account.class);
        cq.select(root)
            .where(cb.equal(root.get(Account_.customerId), customerId));

        TypedQuery<Account> q = em.createQuery(cq);

        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
    }

    private static final Set<String> VALID_COLUMNS_FOR_ORDER_BY = Stream.of("acc_number", "branch_id", "balance")
        .collect(Collectors.toCollection(HashSet::new));

    
    /**
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeFindAccountsByCustomerId(String customerId, String orderBy) {

        String sql = "select " + "customer_id,acc_number,branch_id,balance from Accounts where customer_id = ? ";

        if (VALID_COLUMNS_FOR_ORDER_BY.contains(orderBy)) {
            sql = sql + " order by " + orderBy;
        } else {
            throw new IllegalArgumentException("Nice try!");
        }

        try (Connection c = dataSource.getConnection(); PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, customerId);
            ResultSet rs = p.executeQuery();
            List<AccountDTO> accounts = new ArrayList<>();
            while (rs.next()) {
                AccountDTO acc = AccountDTO.builder()
                    .customerId(rs.getString("customerId"))
                    .branchId(rs.getString("branch_id"))
                    .accNumber(rs.getString("acc_number"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();

                accounts.add(acc);
            }

            return accounts;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    private static final Map<String,SingularAttribute<Account,?>> VALID_JPA_COLUMNS_FOR_ORDER_BY = Stream.of(
        new AbstractMap.SimpleEntry<>(Account_.ACC_NUMBER, Account_.accNumber),
        new AbstractMap.SimpleEntry<>(Account_.BRANCH_ID, Account_.branchId),
        new AbstractMap.SimpleEntry<>(Account_.BALANCE, Account_.balance)
    )
    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
    /**
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeJpaFindAccountsByCustomerId(String customerId, String orderBy) {

SingularAttribute<Account,?> orderByAttribute = VALID_JPA_COLUMNS_FOR_ORDER_BY.get(orderBy);
if ( orderByAttribute == null) {
    throw new IllegalArgumentException("Nice try!");
}

CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Account> cq = cb.createQuery(Account.class);
Root<Account> root = cq.from(Account.class);
cq.select(root)
  .where(cb.equal(root.get(Account_.customerId), customerId))
  .orderBy(cb.asc(root.get(orderByAttribute)));

TypedQuery<Account> q = em.createQuery(cq);

        return q.getResultList()
            .stream()
            .map(a -> AccountDTO.builder()
                .accNumber(a.getAccNumber())
                .balance(a.getBalance())
                .branchId(a.getAccNumber())
                .customerId(a.getCustomerId())
                .build())
            .collect(Collectors.toList());
        
    }
    
    /**
     * Invalid placeholder usage example 
     * 
     * @param tableName 
     * @return
     */
    public Long wrongCountRecordsByTableName(String tableName) {

        try (Connection c = dataSource.getConnection(); PreparedStatement p = c.prepareStatement("select count(*) from ?")) {

            p.setString(1, tableName);
            ResultSet rs = p.executeQuery();
            rs.next();
            return rs.getLong(1);

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Invalid placeholder usage example - JPA
     * 
     * @param tableName 
     * @return
     */
    public Long wrongJpaCountRecordsByTableName(String tableName) {

        String jql = "select count(*) from :tableName";
        TypedQuery<Long> q = em.createQuery(jql, Long.class)
            .setParameter("tableName", tableName);

        return q.getSingleResult();

    }

}
