package com.baeldung.architecture.hexagonal.persistence.postgres.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "Message")
@Table(name = "message")
@Getter
@Setter
public class MessageEntity {
    @Id
    private Long id;
    private String body;
    private String sender;
    private String receiver;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        MessageEntity that = (MessageEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
