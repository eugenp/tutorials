package com.baeldung.jmix.expensetracker.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum ExpenseCategory implements EnumClass<String> {

    EDUCATION("E"),
    FOOD("F"),
    HEALTH("H"),
    HOUSING("HO"),
    TRANSPORTATION("T");

    private final String id;

    ExpenseCategory(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ExpenseCategory fromId(String id) {
        for (ExpenseCategory at : ExpenseCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}