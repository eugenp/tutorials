package com.baeldung.dao.repositories.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.config.PersistenceProductConfiguration;
import com.baeldung.config.PersistenceUserConfiguration;
import com.baeldung.dao.repositories.CustomItemRepository;
import com.baeldung.domain.Item;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = { PersistenceConfiguration.class, PersistenceUserConfiguration.class, PersistenceProductConfiguration.class })
public class CustomItemRepositoryIntegrationTest {

    @Autowired
    CustomItemRepository customItemRepositoryImpl;

    @Autowired
    EntityManager entityManager;

    @Before
    public void setUp() {

        Item firstItem = new Item();
        firstItem.setColor("blue");
        firstItem.setGrade("C");
        firstItem.setId(10l);

        entityManager.persist(firstItem);

        Item secondItem = new Item();
        secondItem.setColor("red");
        secondItem.setGrade("C");
        secondItem.setId(11l);

        entityManager.persist(secondItem);

        Item thirdItem = new Item();
        thirdItem.setColor("blue");
        thirdItem.setGrade("A");
        thirdItem.setId(12l);

        entityManager.persist(thirdItem);

        Item fourthItem = new Item();
        fourthItem.setColor("red");
        fourthItem.setGrade("D");
        fourthItem.setId(13l);

        entityManager.persist(fourthItem);
    }

    @Test
    public void givenItems_whenFindItemsByColorAndGrade_thenReturnItems() {

        List<Item> items = customItemRepositoryImpl.findItemsByColorAndGrade();

        assertFalse("No items found", CollectionUtils.isEmpty(items));
        assertEquals("There should be only one item", 1, items.size());

        Item item = items.get(0);

        assertEquals("this item do not have blue color", "blue", item.getColor());
        assertEquals("this item does not belong to A grade", "A", item.getGrade());
    }

    @Test
    public void givenItems_whenFindItemByColorOrGrade_thenReturnItems() {

        List<Item> items = customItemRepositoryImpl.findItemByColorOrGrade();

        assertFalse("No items found", CollectionUtils.isEmpty(items));
        assertEquals("There should be only one item", 1, items.size());

        Item item = items.get(0);

        assertEquals("this item do not have red color", "red", item.getColor());
        assertEquals("this item does not belong to D grade", "D", item.getGrade());
    }

}
