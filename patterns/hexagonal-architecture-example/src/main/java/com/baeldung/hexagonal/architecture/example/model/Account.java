package com.baeldung.hexagonal.architecture.example.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "sourceAccount")
    private List<Transaction> sourceTransactions;

    @OneToMany(mappedBy = "targetAccount")
    private List<Transaction> targetTransactions;

}