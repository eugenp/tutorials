package com.baeldung.pagination.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Product {

    @Id
    @Getter
    private UUID id;

    @NotNull
    @Size(max = 255, message = "The property 'name' must be less than or equal to 255 characters.")
    private String name;

    @NotNull
    private double price;
}
