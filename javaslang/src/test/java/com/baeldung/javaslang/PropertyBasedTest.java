package com.baeldung.javaslang;


import javaslang.CheckedFunction1;
import javaslang.collection.Stream;
import javaslang.test.Arbitrary;
import javaslang.test.CheckResult;
import javaslang.test.Property;
import org.junit.Test;

public class PropertyBasedTest {

    public Stream<String> stringsSupplier() {
        return Stream.from(0).map(i -> {
            boolean divByTwo = i % 2 == 0;
            boolean divByFive = i % 5 == 0;

            if(divByFive && divByTwo){
                return "DividedByTwoAndFiveWithoutReminder";
            }else if(divByFive){
                return "DividedByFiveWithoutReminder";
            }else if(divByTwo){
                return "DividedByTwoWithoutReminder";
            }
            return "";
        });
    }

    @Test
    public void givenArbitrarySeq_whenCheckThatEverySecondElementIsEqualToString_thenTestPass() {
        //given
        Arbitrary<Integer> multiplesOf2 = Arbitrary.integer()
                .filter(i -> i > 0)
                .filter(i -> i % 2 == 0 && i % 5 != 0);

        //when
        CheckedFunction1<Integer, Boolean> mustEquals =
                i -> stringsSupplier().get(i).equals("DividedByTwoWithoutReminder");


        //then
        CheckResult result = Property
                .def("Every second element must equal to DividedByTwoWithoutReminder")
                .forAll(multiplesOf2)
                .suchThat(mustEquals)
                .check(10_000, 100);

        result.assertIsSatisfied();
    }

    @Test
    public void givenArbitrarySeq_whenCheckThatEveryFifthElementIsEqualToString_thenTestPass() {
        //given
        Arbitrary<Integer> multiplesOf5 = Arbitrary.integer()
                .filter(i -> i > 0)
                .filter(i -> i % 5 == 0 && i % 2 == 0);

        //when
        CheckedFunction1<Integer, Boolean> mustEquals = i ->
                stringsSupplier().get(i).endsWith("DividedByTwoAndFiveWithoutReminder");

        //then
        Property.def("Every fifth element must equal to DividedByTwoAndFiveWithoutReminder")
                .forAll(multiplesOf5)
                .suchThat(mustEquals)
                .check(10_000, 1_000)
                .assertIsSatisfied();
    }
}
