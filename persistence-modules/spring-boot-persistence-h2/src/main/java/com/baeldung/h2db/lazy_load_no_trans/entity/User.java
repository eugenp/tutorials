package com.baeldung.h2db.lazy_load_no_trans.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String comment;

    @OneToMany(mappedBy = "userId")
    @Fetch(FetchMode.SUBSELECT)
    private List<Document> docs = new ArrayList<>();
}
