package com.baeldung.pattern.hexagonal.architecture.application.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {

    @Id
    private Long id;

    private Boolean inUse;

    public boolean rent() {
        if(this.inUse) {
            return false;
        } else {
            this.inUse = true;
            return true;
        }
    }

    public boolean returnRental() {
        if(this.inUse) {
            this.inUse = false;
            return true;
        } else {
            return false;
        }
    }

    //getter and setters
}
