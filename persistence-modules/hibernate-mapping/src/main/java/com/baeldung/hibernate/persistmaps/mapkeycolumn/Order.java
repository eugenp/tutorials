package com.baeldung.hibernate.persistmaps.mapkeycolumn;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ElementCollection
    @CollectionTable(name = "order_item_mapping", joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_name")
    @Column(name = "price")
    private Map<String, Double> itemPriceMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Double> getItemPriceMap() {
        return itemPriceMap;
    }

    public void setItemPriceMap(Map<String, Double> itemPriceMap) {
        this.itemPriceMap = itemPriceMap;
    }
}