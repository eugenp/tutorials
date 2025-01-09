package com.baeldung.spring.data.persistence.truncate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = MyEntity.TABLE_NAME)
public class MyEntity {

    public final static String TABLE_NAME = "my_entity";
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyEntity myEntity = (MyEntity) o;
        return Objects.equals(id, myEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                '}';
    }
}
