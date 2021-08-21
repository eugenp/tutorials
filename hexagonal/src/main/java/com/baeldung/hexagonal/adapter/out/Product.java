package com.baeldung.hexagonal.adapter.out;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.baeldung.hexagonal.domain.CartItem;

import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    public final Integer id;
    @Column
    public final String name;
    @Column
    public final Float price;

    public CartItem toItem() {
        return new CartItem(name, price);
    }

}
