package com.baeldung.convertlisttomap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class ConvertListWithDuplicatedIdToMapServiceUnitTest {
    List<Animal> duplicatedIdList;

    private ConvertListToMapService convertListService = new ConvertListToMapService();

    @Before
    public void init() {

        this.duplicatedIdList = new ArrayList<>();

        Animal cat = new Animal(1, "Cat");
        duplicatedIdList.add(cat);
        Animal dog = new Animal(2, "Dog");
        duplicatedIdList.add(dog);
        Animal pig = new Animal(3, "Pig");
        duplicatedIdList.add(pig);
        Animal cow = new Animal(4, "Cow");
        duplicatedIdList.add(cow);
        Animal goat = new Animal(4, "Goat");
        duplicatedIdList.add(goat);

    }

    @Test
    public void givenADupIdList_whenConvertBeforeJava8_thenReturnMapWithRewrittenElement() {

        Map<Integer, Animal> map = convertListService.convertListBeforeJava8(duplicatedIdList);

        assertThat(map.values(), hasSize(4));
        assertThat(map.values(), hasItem(duplicatedIdList.get(4)));
    }

    @Test
    public void givenADupIdList_whenConvertWithApacheCommons_thenReturnMapWithRewrittenElement() {

        Map<Integer, Animal> map = convertListService.convertListWithApacheCommons(duplicatedIdList);

        assertThat(map.values(), hasSize(4));
        assertThat(map.values(), hasItem(duplicatedIdList.get(4)));
    }

    @Test(expected = IllegalStateException.class)
    public void givenADupIdList_whenConvertAfterJava8_thenException() {

        convertListService.convertListAfterJava8(duplicatedIdList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenADupIdList_whenConvertWithGuava_thenException() {

        convertListService.convertListWithGuava(duplicatedIdList);

    }

}
