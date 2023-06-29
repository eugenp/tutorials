package com.baeldung.java.core.general.copying;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import com.baeldung.java.core.general.copying.cloneable.Article;
import com.baeldung.java.core.general.copying.cloneable.Author;
import com.baeldung.java.core.general.copying.serializable.Building;
import com.baeldung.java.core.general.copying.serializable.Landlord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rits.cloning.Cloner;

public class ExternalLibsUnitTest {

    //region Deep copy
    @Test
    void whenCloneViaObjectUtils_thenShouldReturnNullIfNotCloneable() {
        Landlord landlord = new Landlord("John", "Doe");
        Landlord landlordClone = (Landlord) ObjectUtils.clone(landlord);

        Assertions.assertNull(landlordClone);
    }

    @Test
    void whenCloneViaObjectUtils_thenShouldCloneIfCloneable() {
        Author author = new Author(111, "John", "Doe");
        Article article = new Article(25, "Some title", author);

        Article articleClone = (Article) ObjectUtils.clone(article);

        //checking that objects are NOT equal by reference
        assertNotSame(article.getAuthor(), articleClone.getAuthor());
        assertEquals(article.getAuthor(), articleClone.getAuthor());

        assertEquals(article, articleClone);
    }

    @Test
    void whenCloneViaSerializableUtils_thenShouldReturnDeepCopy() {
        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        Building buildingClone = (Building) SerializationUtils.clone(building);

        //checking that objects are NOT equal by reference
        assertNotSame(building.getLandlord(), buildingClone.getLandlord());
        assertEquals(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }

    @Test
    void whenCloneViaJackson_thenShouldReturnDeepCopy() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        String buildingString = objectMapper.writeValueAsString(building);
        Building buildingClone = objectMapper.readValue(buildingString, Building.class);

        //checking that objects are NOT equal by reference
        assertNotSame(building.getLandlord(), buildingClone.getLandlord());
        assertEquals(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }

    @Test
    void whenCloneViaCloner_thenShouldReturnDeepCopy() {

        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        Building buildingClone = new Cloner().deepClone(building);

        //checking that objects are not equal by reference
        assertNotSame(building.getLandlord(), buildingClone.getLandlord());
        assertEquals(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }

    //endregion
    //region Shallow copy
    @Test
    void whenCloneViaSpringBeanUtils_thenShouldReturnShallowCopy() {

        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        Building buildingClone = new Building();
        BeanUtils.copyProperties(building, buildingClone);

        //checking that objects are equal by reference
        assertSame(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }

    @Test
    void whenCloneViaApacheBeanUtils_thenShouldReturnShallowCopy() throws Exception {

        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        Building buildingClone = new Building();
        org.apache.commons.beanutils.BeanUtils.copyProperties(buildingClone, building);

        //checking that objects are equal by reference
        assertSame(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }

    @Test
    void whenCloneViaApacheBeanUtilsClone_thenShouldReturnShallowCopy() throws Exception {

        Landlord landlord = new Landlord("John", "Doe");
        Building building = new Building("Alabama street 25", "Some status", landlord);

        Building buildingClone = (Building) org.apache.commons.beanutils.BeanUtils.cloneBean(building);

        //checking that objects are equal by reference
        assertSame(building.getLandlord(), buildingClone.getLandlord());

        assertEquals(building, buildingClone);
    }
    //endregion
}
