package com.baeldung.convertlisttomap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class ConvertListToMapServiceUnitTest {
    List<Animal> list;

    private ConvertListToMapService convertListService;

    @Before
    public void init() {
        this.convertListService = new ConvertListToMapService();
        this.list = new ArrayList<>();

        Animal cat = new Animal(1, "Cat");
        list.add(cat);
        Animal dog = new Animal(2, "Dog");
        list.add(dog);
        Animal pig = new Animal(3, "Pig");
        list.add(pig);
        Animal cow = new Animal(4, "Cow");
        list.add(cow);
        Animal goat = new Animal(5, "Goat");
        list.add(goat);

    }

    @Test
    public void givenAList_whenConvertBeforeJava8_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListBeforeJava8(list);

        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }

    @Test
    public void givenAList_whenConvertAfterJava8_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListAfterJava8(list);

        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }

    @Test
    public void givenAList_whenConvertWithGuava_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListWithGuava(list);

        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }

    @Test
    public void givenAList_whenConvertWithApacheCommons_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListWithApacheCommons(list);

        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }

}
