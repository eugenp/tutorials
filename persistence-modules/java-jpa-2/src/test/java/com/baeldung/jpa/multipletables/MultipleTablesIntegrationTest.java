package com.baeldung.jpa.multipletables;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.jpa.multipletables.multipleentities.MealWithMultipleEntities;
import com.baeldung.jpa.multipletables.secondarytable.MealAsSingleEntity;
import com.baeldung.jpa.multipletables.secondarytable.embeddable.MealWithEmbeddedAllergens;

public class MultipleTablesIntegrationTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("jpa-h2-multipltables");
        em = emf.createEntityManager();
    }

    @Test
    public void entityManager_shouldLoadMealAsSingleEntity() {
        // given
        
        // when
        MealAsSingleEntity meal = em.find(MealAsSingleEntity.class, 1L);
        
        // then
        assertThat(meal).isNotNull();
        assertThat(meal.getId()).isEqualTo(1L);
        assertThat(meal.isPeanuts()).isFalse();
        assertThat(meal.isCelery()).isTrue();
    }

    @Test
    public void entityManager_shouldLoadMealWithEmbeddedAllergens() {
        // given
        
        // when
        MealWithEmbeddedAllergens meal = em.find(MealWithEmbeddedAllergens.class, 1L);
        
        // then
        assertThat(meal).isNotNull();
        assertThat(meal.getId()).isEqualTo(1L);
        assertThat(meal.getAllergens()).isNotNull();
        assertThat(meal.getAllergens().isPeanuts()).isFalse();
        assertThat(meal.getAllergens().isCelery()).isTrue();
    }

    @Test
    public void entityManager_shouldLoadMealWithAllergensEntity() {
        // given
        
        // when
        MealWithMultipleEntities meal = em.find(MealWithMultipleEntities.class, 1L);
        
        // then
        assertThat(meal).isNotNull();
        assertThat(meal.getId()).isEqualTo(1L);
        assertThat(meal.getAllergens()).isNotNull();
        assertThat(meal.getAllergens().isPeanuts()).isFalse();
        assertThat(meal.getAllergens().isCelery()).isTrue();
    }

    @AfterClass
    public static void teardown() {
        if (emf != null) {
            emf.close();
        }
    }

}
