/**
 * 
 */
package com.baeldung.examples.security.sql;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * @author Philippe
 *
 */
@Entity
@Table(name="Accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String customerId;
    private String accNumber;
    private String branchId;
    private BigDecimal balance;

}
