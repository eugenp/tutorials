package com.account.service.repository.jpa;

import com.account.service.domain.TransferResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "money_transfers")
public class TransferEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String senderAccountId;

  @Column(nullable = false)
  private String receiverAccountId;

  @Column(nullable = false)
  private BigInteger amount;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private TransferResult result;

  @Column(nullable = true)
  private String detail;

  @Column(nullable = false)
  private Instant createdAt;
}
