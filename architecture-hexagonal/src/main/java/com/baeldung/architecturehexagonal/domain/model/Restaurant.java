package com.baeldung.architecturehexagonal.domain.model;

import java.util.Set;

public class Restaurant {
    private final String name;
    private final Set<Table> tables;

    public Restaurant(String name, Set<Table> tables) {
        this.name = name;
        this.tables = tables;
    }

    public static Restaurant of(String name, Set<Table> tables) {
        return new Restaurant(name, tables);
    }

    public String getName() {
        return name;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public Integer getTotalCapacity() {
        return tables.stream().mapToInt(Table::getCapacity).sum();
    }
}
