package com.baeldung.javaslang;

import javaslang.CheckedFunction1;
import javaslang.collection.Stream;
import javaslang.test.Arbitrary;
import javaslang.test.CheckResult;
import javaslang.test.Property;
import org.junit.Test;

import java.util.function.Predicate;

import static javaslang.API.*;

public class PropertyBasedLongRunningUnitTest {

    private static Predicate<Integer> divisibleByTwo = i -> i % 2 == 0;
    private static Predicate<Integer> divisibleByFive = i -> i % 5 == 0;

    private Stream<String> stringsSupplier() {
        return Stream.from(0).map(i -> Match(i).of(
          Case($(divisibleByFive.and(divisibleByTwo)), "DividedByTwoAndFiveWithoutRemainder"),
          Case($(divisibleByFive), "DividedByFiveWithoutRemainder"),
          Case($(divisibleByTwo), "DividedByTwoWithoutRemainder"),
          Case($(), "")));
    }

    @Test
    public void givenArbitrarySeq_whenCheckThatEverySecondElementIsEqualToString_thenTestPass() {
        //given
        Arbitrary<Integer> multiplesOf2 = Arbitrary
          .integer()
          .filter(i -> i > 0)
          .filter(i -> i % 2 == 0 && i % 5 != 0);

        //when
        CheckedFunction1<Integer, Boolean> mustEquals = i -> stringsSupplier()
          .get(i)
          .equals("DividedByTwoWithoutRemainder");

        //then
        CheckResult result = Property
          .def("Every second element must equal to DividedByTwoWithoutRemainder")
          .forAll(multiplesOf2)
          .suchThat(mustEquals)
          .check(10_000, 100);

        result.assertIsSatisfied();
    }

    @Test
    public void givenArbitrarySeq_whenCheckThatEveryFifthElementIsEqualToString_thenTestPass() {
        //given
        Arbitrary<Integer> multiplesOf5 = Arbitrary
          .integer()
          .filter(i -> i > 0)
          .filter(i -> i % 5 == 0 && i % 2 == 0);

        //when
        CheckedFunction1<Integer, Boolean> mustEquals = i -> stringsSupplier()
          .get(i)
          .endsWith("DividedByTwoAndFiveWithoutRemainder");

        //then
        Property
          .def("Every fifth element must equal to DividedByTwoAndFiveWithoutRemainder")
          .forAll(multiplesOf5)
          .suchThat(mustEquals)
          .check(10_000, 1_000)
          .assertIsSatisfied();
    }
}
