package com.baeldung.rxjava.jdbc;

import com.github.davidmoten.rx.jdbc.annotations.Column;

public interface Employee {

    @Column("id")
    int id();

    @Column("name")
    String name();
    
}
