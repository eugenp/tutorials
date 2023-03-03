package com.baeldung.examples.r2dbc.flyway.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("student")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Persistable<UUID> {

    @Id
    @JsonProperty("uuid")
    @JsonAlias("id")
    private UUID id;

    @NotNull
    @Size(max = 255, message = "The property 'firstName' must be less than or equal to 255 characters.")
    private String firstName;

    @NotNull
    @Size(max = 255, message = "The property 'lastName' must be less than or equal to 255 characters.")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull
    private UUID department;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }
}

