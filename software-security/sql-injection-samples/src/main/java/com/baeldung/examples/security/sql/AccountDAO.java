/**
 * 
 */
package com.baeldung.examples.security.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * @author Philippe
 *
 */
@Component
public class AccountDAO {

    private final DataSource dataSource;

    public AccountDAO(DataSource dataSource) {
        this.dataSource = dataSource;
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
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeFindAccountsByCustomerId(String customerId) {

        String sql = "select " + "customer_id,acc_number,branch_id,balance from Accounts where customer_id = ?";

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

    private static final Set<String> VALID_COLUMNS_FOR_ORDER_BY = Stream.of("acc_number", "branch_id", "balance")
        .collect(Collectors.toCollection(HashSet::new));

    /**
     * Return all accounts owned by a given customer,given his/her external id
     * 
     * @param customerId
     * @return
     */
    public List<AccountDTO> safeFindAccountsByCustomerId(String customerId, String orderBy)  {

        String sql = "select " + "customer_id,acc_number,branch_id,balance from Accounts where customer_id = ? ";

        if (VALID_COLUMNS_FOR_ORDER_BY.contains(orderBy)) {
            sql = sql + " order by " + orderBy;
        }
        else {
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

    /**
     * Invalid placeholder usage example 
     * 
     * @param tableName 
     * @return
     */
    public List<AccountDTO> wrongCountRecordsByTableName(String tableName) {

        try (Connection c = dataSource.getConnection(); 
             PreparedStatement p = c.prepareStatement("select count(*) from ?")) {
            
            p.setString(1, tableName);
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

}
