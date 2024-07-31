package com.baeldung.dbview;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "SHOP_SALE_VIEW")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ShopSaleVid {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "shop_id")
    private int shopId;

    @Column(name = "shop_location", length = 100)
    private String shopLocation;

    @Column(name = "transaction_year")
    private int year;

    @Column(name = "transaction_month")
    private int month;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}