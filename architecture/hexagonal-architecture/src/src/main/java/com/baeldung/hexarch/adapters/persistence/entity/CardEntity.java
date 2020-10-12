package com.baeldung.hexarch.adapters.persistence.entity;

import com.baeldung.hexarch.application.domain.Card;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@AllArgsConstructor
@Entity(name = "card")
public class CardEntity {
    @Id
    private Long id;
    private BigDecimal balance;
    private BigDecimal creditBalance;
    private Boolean isDebit;

    public Card entityToModel(){
        return new Card(id, balance, creditBalance, isDebit);
    }
}
