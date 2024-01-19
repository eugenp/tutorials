package com.baeldung.listvsset.eager.set.moderatedomain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
}
