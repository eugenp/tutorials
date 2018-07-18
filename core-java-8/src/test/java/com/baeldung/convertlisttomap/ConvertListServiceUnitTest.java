package com.baeldung.convertlisttomap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ConvertListServiceUnitTest {
    List<Animal> list;

    private ConvertListService convertListService;

    @Before
    public void init() {
        this.convertListService = new ConvertListService();
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
    public void givenAList_whenConvertWithApacheCommons1_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListWithApacheCommons1(list);
        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }

    @Test
    public void givenAList_whenConvertWithApacheCommons2_thenReturnMapWithTheSameElements() {

        Map<Integer, Animal> map = convertListService.convertListWithApacheCommons2(list);
        assertThat(map.values(), containsInAnyOrder(list.toArray()));
    }
}
