package com.baeldung.parameterpadding;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import jakarta.persistence.EntityManager;

@SpringBootTest(classes = Application.class)
@Sql(scripts = { "/pokemon-schema.sql", "/pokemon-data.sql" })
class ParameterPaddingIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void whenQueryingPokemonByName_thenCorrectPokemonEntityIsRetrieved() {
        String[] names = { "Pikachu", "Charizard", "Bulbasaur" };
        String query = "SELECT p FROM Pokemon p WHERE p.name = :name";

        for (String name : names) {
            Pokemon pokemon = entityManager.createQuery(query, Pokemon.class)
                .setParameter("name", name)
                .getSingleResult();

            assertThat(pokemon)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
        }
    }

    @Test
    void whenQueryingPokemonByNameGroups_thenCorrectPokemonEntitiesAreRetrieved() {
        String[][] nameGroups = { 
            { "Jigglypuff" },
            { "Snorlax", "Squirtle" },
            { "Pikachu", "Charizard", "Bulbasaur" } 
        };
        String query = "SELECT p FROM Pokemon p WHERE p.name IN :names";

        for (String[] names : nameGroups) {
            List<Pokemon> pokemons = entityManager.createQuery(query, Pokemon.class)
                .setParameter("names", Arrays.asList(names))
                .getResultList();

            assertThat(pokemons)
                .isNotEmpty();
        }
    }

}