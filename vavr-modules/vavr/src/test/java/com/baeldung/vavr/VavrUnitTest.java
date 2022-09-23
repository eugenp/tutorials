package com.baeldung.vavr;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function5;
import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VavrUnitTest {
    @Test
    public void givenList_whenSorts_thenCorrect() {
        List<Integer> sortedList = List.of(3, 2, 1)
          .sorted();
    }

    /*
     *  Tuples
     */
    // creating and element access
    @Test
    public void whenCreatesTuple_thenCorrect1() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        String element1 = java8._1;
        int element2 = java8._2();

        assertEquals("Java", element1);
        assertEquals(8, element2);
    }

    @Test
    public void whenCreatesTuple_thenCorrect2() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, 1.8);
        String element1 = java8._1;
        int element2 = java8._2();
        double element3 = java8._3();

        assertEquals("Java", element1);
        assertEquals(8, element2);
        assertEquals(1.8, element3, 0.1);
    }

    // mapping--component-wise(using Function interface)
    @Test
    public void givenTuple_whenMapsComponentWise_thenCorrect() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        Tuple2<String, Integer> mapOfJava8 = java8.map(s -> s + "Vavr", i -> i / 2);
        int num = mapOfJava8._2();
        assertEquals("JavaVavr", mapOfJava8._1);

        assertEquals(4, num);

    }

    // mapping--with one mapper(using BiFunction interface)
    @Test
    public void givenTuple_whenMapsWithOneMapper_thenCorrect() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        Tuple2<String, Integer> mapOfJava8 = java8.map((s, i) -> Tuple.of(s + "Vavr", i / 2));
        int num = mapOfJava8._2();
        assertEquals("JavaVavr", mapOfJava8._1);

        assertEquals(4, num);
    }

    // transforming a tuple
    @Test
    public void givenTuple_whenTransforms_thenCorrect() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        String transformed = java8.apply((s, i) -> s + "Vavr " + i / 2);
        assertEquals("JavaVavr 4", transformed);
    }

    @Test
    public void editTupleValueForNewTupleInstance(){
        final Tuple2<String, Integer> java9 = Tuple.of("Java", 8);
        final Tuple2<String, Integer> transformed = java9.update2(9);
        int num = transformed._2();
        assertEquals(9,num);
    }

    @Test
    public void editTupleValueForSameInstance(){
        Tuple2<String, Integer> java9 = Tuple.of("Java", 8);
        java9 = java9.update2(9);
        final int num = java9._2();
        assertEquals(9,num);
    }

    @Test
    public void getNumberOfElementTuple(){
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        Tuple3<String, Integer, Double> java8Triple = Tuple.of("Java", 8, 1.8);
        Tuple3<String, Integer, Double> java8TripleWnull = Tuple.of("Java", null, 1.8);

        int num = java8.arity();
        int numTriple = java8Triple.arity();
        int numTripleWnull = java8TripleWnull.arity();
        assertEquals(2,num);
        assertEquals(3,numTriple);
        assertEquals(3,numTripleWnull);
    }


    /*
     * Functions
     */
    @Test
    public void givenJava8Function_whenWorks_thenCorrect() {
        Function<Integer, Integer> square = (num) -> num * num;
        int result = square.apply(2);
        assertEquals(4, result);
    }

    @Test
    public void givenJava8BiFunction_whenWorks_thenCorrect() {
        BiFunction<Integer, Integer, Integer> sum = (num1, num2) -> num1 + num2;
        int result = sum.apply(5, 7);
        assertEquals(12, result);
    }

    @Test
    public void givenVavrFunction_whenWorks_thenCorrect() {
        Function1<Integer, Integer> square = (num) -> num * num;
        Integer result = square.apply(2);
        assertEquals(Integer.valueOf(4), result);
    }

    @Test
    public void givenVavrBiFunction_whenWorks_thenCorrect() {
        Function2<Integer, Integer, Integer> sum = (num1, num2) -> num1 + num2;
        Integer result = sum.apply(5, 7);
        assertEquals(Integer.valueOf(12), result);
    }

    @Test
    public void whenCreatesFunction_thenCorrect0() {
        Function0<String> getClazzName = () -> this.getClass()
          .getName();
        String clazzName = getClazzName.apply();
        assertEquals("com.baeldung.vavr.VavrUnitTest", clazzName);
    }

    @Test
    public void whenCreatesFunction_thenCorrect2() {
        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        int summed = sum.apply(5, 6);
        assertEquals(11, summed);
    }

    @Test
    public void whenCreatesFunction_thenCorrect5() {
        Function5<String, String, String, String, String, String> concat = (a, b, c, d, e) -> a + b + c + d + e;
        String finalString = concat.apply("Hello ", "world", "! ", "Learn ", "Vavr");
        assertEquals("Hello world! Learn Vavr", finalString);
    }

    @Test
    public void whenCreatesFunctionFromMethodRef_thenCorrect() {
        Function2<Integer, Integer, Integer> sum = Function2.of(this::sum);
        int summed = sum.apply(5, 6);
        assertEquals(11, summed);
    }

    public int sum(int a, int b) {
        return a + b;
    }

    /*
     * Values
     */
    // option
    @Test
    public void givenValue_whenNullCheckNeeded_thenCorrect() {
        Object possibleNullObj = null;
        if (possibleNullObj == null)
            possibleNullObj = "someDefaultValue";
        assertNotNull(possibleNullObj);
    }

    @Test(expected = NullPointerException.class)
    public void givenValue_whenNullCheckNeeded_thenCorrect2() {
        Object possibleNullObj = null;
        assertEquals("somevalue", possibleNullObj.toString());
    }

    @Test
    public void givenValue_whenCreatesOption_thenCorrect() {
        Option<Object> noneOption = Option.of(null);
        Option<Object> someOption = Option.of("val");
        assertEquals("None", noneOption.toString());
        assertEquals("Some(val)", someOption.toString());
    }

    @Test
    public void givenNull_whenCreatesOption_thenCorrect() {
        String name = null;
        Option<String> nameOption = Option.of(name);
        assertEquals("baeldung", nameOption.getOrElse("baeldung"));
    }

    @Test
    public void givenNonNull_whenCreatesOption_thenCorrect() {
        String name = "baeldung";
        Option<String> nameOption = Option.of(name);
        assertEquals("baeldung", nameOption.getOrElse("notbaeldung"));
    }

    // try
    @Test(expected = ArithmeticException.class)
    public void givenBadCode_whenThrowsException_thenCorrect() {
        int i = 1 / 0;
    }

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        assertTrue(result.isFailure());
    }

    @Test
    public void givenBadCode_whenTryHandles_thenCorrect2() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        int errorSentinel = result.getOrElse(-1);
        assertEquals(-1, errorSentinel);
    }

     @Test(expected = RuntimeException.class)
     public void givenBadCode_whenTryHandles_thenCorrect3() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        result.getOrElseThrow(e->new RuntimeException(e));//re-throw different ex type
     }

    // lazy
    @Test
    public void givenFunction_whenEvaluatesWithLazy_thenCorrect() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        assertFalse(lazy.isEvaluated());

        double val1 = lazy.get();
        assertTrue(lazy.isEvaluated());

        double val2 = lazy.get();
        assertEquals(val1, val2, 0.1);
    }

    // validation
    @Test
    public void whenValidationWorks_thenCorrect() {
        PersonValidator personValidator = new PersonValidator();
        Validation<Seq<String>, Person> valid = personValidator.validatePerson("John Doe", 30);
        Validation<Seq<String>, Person> invalid = personValidator.validatePerson("John? Doe!4", -1);

        assertEquals("Valid(Person [name=John Doe, age=30])", valid.toString());
        assertEquals("Invalid(List(Invalid characters in name: ?!4, Age must be at least 0))", invalid.toString());
    }

    /*
     * collections
     */
    // list
    @Test(expected = UnsupportedOperationException.class)
    public void whenImmutableCollectionThrows_thenCorrect() {
        java.util.List<String> wordList = Arrays.asList("abracadabra");
        java.util.List<String> list = Collections.unmodifiableList(wordList);
        list.add("boom");
    }

    @Test
    public void whenSumsJava8List_thenCorrect() {
        // Arrays.asList(1, 2, 3).stream().reduce((i, j) -> i + j);
        int sum = IntStream.of(1, 2, 3)
          .sum();
        assertEquals(6, sum);
    }

    @Test
    public void whenCreatesVavrList_thenCorrect() {
        List<Integer> intList = List.of(1, 2, 3);
        assertEquals(3, intList.length());
        assertEquals(new Integer(1), intList.get(0));
        assertEquals(new Integer(2), intList.get(1));
        assertEquals(new Integer(3), intList.get(2));
    }

    @Test
    public void whenSumsVavrList_thenCorrect() {
        int sum = List.of(1, 2, 3)
          .sum()
          .intValue();
        assertEquals(6, sum);
    }

    /*
     * pattern matching
     */
    @Test
    public void whenIfWorksAsMatcher_thenCorrect() {
        int input = 3;
        String output;
        if (input == 0) {
            output = "zero";
        }
        if (input == 1) {
            output = "one";
        }
        if (input == 2) {
            output = "two";
        }
        if (input == 3) {
            output = "three";
        } else {
            output = "unknown";
        }
        assertEquals("three", output);
    }

    @Test
    public void whenSwitchWorksAsMatcher_thenCorrect() {
        int input = 2;
        String output;
        switch (input) {
            case 0:
                output = "zero";
                break;
            case 1:
                output = "one";
                break;
            case 2:
                output = "two";
                break;
            case 3:
                output = "three";
                break;
            default:
                output = "unknown";
                break;
        }
        assertEquals("two", output);
    }

    @Test
    public void whenMatchworks_thenCorrect() {
        int input = 2;
        String output = Match(input).of(Case($(1), "one"), Case($(2), "two"), Case($(3), "three"), Case($(), "?"));
        assertEquals("two", output);
    }

}
