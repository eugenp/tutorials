package com.baeldung.listvsset.eager.set.fulldomain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.Set;
import lombok.Data;

@Data
@Entity(name = "interest_group")
@Table(name = "interest_group")
public class Group {

    @Id
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        return Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
