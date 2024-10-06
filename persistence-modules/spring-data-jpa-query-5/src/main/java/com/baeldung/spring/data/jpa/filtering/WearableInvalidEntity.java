package com.baeldung.spring.data.jpa.filtering;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wearables")
public class WearableInvalidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String Name;

    @Column(name = "price")
    private BigDecimal Price;

    @Column(name = "sensor_type")
    private String SensorType;

    @Column(name = "popularity_index")
    private Integer PopularityIndex;

    public Long getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public String getSensorType() {
        return SensorType;
    }

    public Integer getPopularityIndex() {
        return PopularityIndex;
    }

}
