package com.baeldung.attributevaluegenerator;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "wizards")
public class Wizard {

    @Id
    private UUID id;

    private String name;

    @SortHouse
    private String house;
    
    @GenerateSpellPower
    private Integer spellPower;

    @GenerateUpdatedAtTimestamp
    private LocalDateTime updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public Integer getSpellPower() {
        return spellPower;
    }

}
