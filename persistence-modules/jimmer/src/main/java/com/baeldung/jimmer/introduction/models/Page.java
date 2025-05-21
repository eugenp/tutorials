package com.baeldung.jimmer.introduction.models;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.jetbrains.annotations.NotNull;

@Entity
public interface Page {

    @Id
    @GeneratedValue(strategy = GenerationType.USER)
    long id();

    String content();

    int number();

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book();
}
