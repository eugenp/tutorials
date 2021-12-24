package com.baeldung.dddhexagonalsimple.adapters.persistence;

import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PizzaOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private PizzaEntity pizza;

    private Integer diameterInInches;

    private BigDecimal price;

    public static PizzaOrderEntity fromDomain(PizzaOrder domainObject) {
        return PizzaOrderEntity.builder().id(domainObject.getId()).pizza(PizzaEntity.fromDomain(domainObject.getPizza())).diameterInInches(domainObject.getDiameterInInches()).price(domainObject.getPrice()).build();
    }

    public PizzaOrder toDomain() {
        return PizzaOrder.builder().id(id).pizza(pizza.toDomain()).diameterInInches(diameterInInches).price(price).build();
    }
}
