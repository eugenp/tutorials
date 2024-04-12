package com.baeldung.dbview;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
public class ShopSale {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride( name = "shopId", column = @Column(name = "shop_id")),
            @AttributeOverride( name = "year", column = @Column(name = "transaction_year")),
            @AttributeOverride( name = "month", column = @Column(name = "transaction_month"))
    })
    private ShopSaleCompositeId id;

    @Column(name = "shop_location", length = 100)
    private String shopLocation;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

}