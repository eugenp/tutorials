package com.baeldung.spring.data.jpa.filtering;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wearables")
public class WearableValidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "sensor_type")
    private String sensorType;

    @Column(name = "popularity_index")
    private Integer popularityIndex;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSensorType() {
        return sensorType;
    }

    public Integer getPopularityIndex() {
        return popularityIndex;
    }

}
