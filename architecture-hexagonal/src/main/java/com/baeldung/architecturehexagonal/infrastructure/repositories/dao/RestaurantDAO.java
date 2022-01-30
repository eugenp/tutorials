package com.baeldung.architecturehexagonal.infrastructure.repositories.dao;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.baeldung.architecturehexagonal.domain.model.Restaurant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantDAO {
    @Id
    private String name;
    @OneToMany
    private Set<TableDAO> tables;

    public static RestaurantDAO fromRestaurant(Restaurant restaurant) {
        return new RestaurantDAO(restaurant.getName(), restaurant.getTables()
            .stream()
            .map(TableDAO::fromTable)
            .collect(Collectors.toSet()));
    }

    public Restaurant toRestaurant() {
        return Restaurant.of(this.name, this.tables.stream()
            .map(TableDAO::toTable)
            .collect(Collectors.toSet()));
    }
}
