package com.baeldung.examples.r2dbc.flyway.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Persistable<UUID> {

    @Id
    @JsonProperty("uuid")
    @JsonAlias("id")
    private UUID id;

    @NotNull
    @Size(max = 255, message = "The property 'name' must be less than or equal to 255 characters.")
    private String name;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }
}
