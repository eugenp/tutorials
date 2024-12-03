package com.baeldung.jmix.expensetracker.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Table(name = "EXPENSE", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_EXPENSE_UNQ", columnNames = {"NAME"})
})
@Entity
public class Expense {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "CATEGORY", nullable = false)
    @NotNull
    private String category;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    public ExpenseCategory getCategory() {
        return category == null ? null : ExpenseCategory.fromId(category);
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category == null ? null : category.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}