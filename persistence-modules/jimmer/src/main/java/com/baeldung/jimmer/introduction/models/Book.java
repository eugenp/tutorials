package com.baeldung.jimmer.introduction.models;

import java.time.Instant;
import java.util.List;

import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OneToMany;

@Entity
public interface Book {
    @Id
    @GeneratedValue(strategy = GenerationType.USER)
    long id();

    @Column(name = "title")
    String title();

    @Column(name = "created_at")
    Instant createdAt();

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author();

    @OneToMany(mappedBy = "book")
    List<Page> pages();
}
