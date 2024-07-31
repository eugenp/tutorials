package com.baeldung.hibernate.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.locationtech.jts.geom.Polygon;

@Entity
public class PolygonEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Polygon polygon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public String toString() {
        return "PolygonEntity{" + "id=" + id + ", polygon=" + polygon + '}';
    }
}
