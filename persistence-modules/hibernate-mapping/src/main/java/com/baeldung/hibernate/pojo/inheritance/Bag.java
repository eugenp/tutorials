package com.baeldung.hibernate.pojo.inheritance;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Bag implements Item {

    @Id
    private long bagId;

    private String type;

    public Bag(long bagId, String type) {
        this.bagId = bagId;
        this.type = type;
    }

    public long getBagId() {
        return bagId;
    }

    public void setBagId(long bagId) {
        this.bagId = bagId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
