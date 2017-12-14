package com.baeldung.hibernate.pojo;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PointEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Point point;

    public PointEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "PointEntity{" + "id=" + id + ", point=" + point + '}';
    }
}
