package com.baeldung.jimmer.introduction.models;

import java.util.List;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.OneToMany;

@Entity
public interface Author {

    @Id
    @GeneratedValue(strategy = GenerationType.USER)
    long id();

    String firstName();

    String lastName();

    @OneToMany(mappedBy = "author")
    List<Book> books();
}
