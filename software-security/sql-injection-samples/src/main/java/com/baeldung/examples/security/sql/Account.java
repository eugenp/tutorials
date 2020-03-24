/**
 * 
 */
package com.baeldung.examples.security.sql;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
