package com.baeldung.hexagonal.architecture.example.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

    @Column(name = "time")
    @Builder.Default
    private OffsetDateTime time = OffsetDateTime.now();

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "details")
    private String details;

    @Column(name = "reference_no")
    private Long referenceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    @ToString.Exclude
    private Account sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", nullable = false)
    @ToString.Exclude
    private Account targetAccount;

}