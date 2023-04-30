package com.baeldung.hibernate.uuids;

import java.util.UUID;
import java.time.LocalDate;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import org.hibernate.annotations.UuidGenerator;

@Entity
public class WebSiteUser {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    private LocalDate registrationDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
