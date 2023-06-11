package com.baeldung.countrows.entity;

import javax.persistence.*;

@Entity
@Table(name = "PERMISSIONS")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_id_sq")
    @SequenceGenerator(name = "permissions_id_sq", sequenceName = "permissions_id_sq", allocationSize = 1)
    private int id;

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Permission{" + "id=" + id + ", type='" + type + '\'' + '}';
    }
}
