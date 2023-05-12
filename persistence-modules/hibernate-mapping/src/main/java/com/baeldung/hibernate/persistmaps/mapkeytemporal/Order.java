package com.baeldung.hibernate.persistmaps.mapkeytemporal;

import com.baeldung.hibernate.persistmaps.mapkey.Item;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyTemporal;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.TemporalType;
import java.util.Date;
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
    @MapKeyTemporal(TemporalType.TIMESTAMP)
    private Map<Date, Item> itemMap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Date, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Date, Item> itemMap) {
        this.itemMap = itemMap;
    }
}
