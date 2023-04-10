package com.baeldung.hibernate.persistmaps.mapkeyjoincolumn;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_item_mapping", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "seller_id")
    private Map<Seller, Item> sellerItemMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Seller, Item> getSellerItemMap() {
        return sellerItemMap;
    }

    public void setSellerItemMap(Map<Seller, Item> sellerItemMap) {
        this.sellerItemMap = sellerItemMap;
    }
}