package com.baeldung.java8.lambda.methodreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class MethodReferenceUnitTest {

    private static <T> void doNothingAtAll(Object... o) {
    }

    ;
    
    @Test
    public void referenceToStaticMethod() {
        List<String> messages = Arrays.asList("Hello", "Baeldung", "readers!");
        messages.forEach(word -> StringUtils.capitalize(word));
        messages.forEach(StringUtils::capitalize);
    }

    @Test
    public void referenceToInstanceMethodOfParticularObject() {
        BicycleComparator bikeFrameSizeComparator = new BicycleComparator();
        createBicyclesList().stream()
            .sorted((a, b) -> bikeFrameSizeComparator.compare(a, b));
        createBicyclesList().stream()
            .sorted(bikeFrameSizeComparator::compare);
    }

    @Test
    public void referenceToInstanceMethodOfArbitratyObjectOfParticularType() {
        List<Integer> numbers = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
        numbers.stream()
            .sorted((a, b) -> a.compareTo(b));
        numbers.stream()
            .sorted(Integer::compareTo);
    }

    @Test
    public void referenceToConstructor() {
        BiFunction<String, Integer, Bicycle> bikeCreator = (brand, frameSize) -> new Bicycle(brand, frameSize);
        BiFunction<String, Integer, Bicycle> bikeCreatorMethodReference = Bicycle::new;
        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(bikeCreator.apply("Giant", 50));
        bikes.add(bikeCreator.apply("Scott", 20));
        bikes.add(bikeCreatorMethodReference.apply("Trek", 35));
        bikes.add(bikeCreatorMethodReference.apply("GT", 40));
    }

    @Test
    public void referenceToConstructorSimpleExample() {
        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "Trek", "GT");
        bikeBrands.stream()
            .map(Bicycle::new)
            .toArray(Bicycle[]::new);
    }

    @Test
    public void limitationsAndAdditionalExamples() {
        createBicyclesList().forEach(b -> System.out.printf("Bike brand is '%s' and frame size is '%d'%n", b.getBrand(), b.getFrameSize()));
        createBicyclesList().forEach((o) -> MethodReferenceUnitTest.doNothingAtAll(o));
    }

    private List<Bicycle> createBicyclesList() {
        List<Bicycle> bikes = new ArrayList<>();
        bikes.add(new Bicycle("Giant", 50));
        bikes.add(new Bicycle("Scott", 20));
        bikes.add(new Bicycle("Trek", 35));
        bikes.add(new Bicycle("GT", 40));
        return bikes;
    }

}
