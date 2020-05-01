package com.baeldung.hamcrest;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestNumberUnitTest {

    @Test
    public void givenADouble_whenCloseTo_thenCorrect() {
        double actual = 1.3;
        double operand = 1;
        double error = 0.5;
        assertThat(actual, is(closeTo(operand, error)));
    }

    @Test
    public void givenADouble_whenNotCloseTo_thenCorrect() {
        double actual = 1.6;
        double operand = 1;
        double error = 0.5;
        assertThat(actual, is(not(closeTo(operand, error))));
    }

    @Test
    public void givenABigDecimal_whenCloseTo_thenCorrect() {
        BigDecimal actual = new BigDecimal("1.0003");
        BigDecimal operand = new BigDecimal("1");
        BigDecimal error = new BigDecimal("0.0005");
        assertThat(actual, is(closeTo(operand, error)));
    }

    @Test
    public void givenABigDecimal_whenNotCloseTo_thenCorrect() {
        BigDecimal actual = new BigDecimal("1.0006");
        BigDecimal operand = new BigDecimal("1");
        BigDecimal error = new BigDecimal("0.0005");
        assertThat(actual, is(not(closeTo(operand, error))));
    }

    @Test
    public void given5_whenComparesEqualTo5_thenCorrect() {
        Integer five = 5;
        assertThat(five, comparesEqualTo(five));
    }

    @Test
    public void given5_whenNotComparesEqualTo7_thenCorrect() {
        Integer seven = 7;
        Integer five = 5;
        assertThat(five, not(comparesEqualTo(seven)));
    }

    @Test
    public void given7_whenGreaterThan5_thenCorrect() {
        Integer seven = 7;
        Integer five = 5;
        assertThat(seven, is(greaterThan(five)));
    }

    @Test
    public void given7_whenGreaterThanOrEqualTo5_thenCorrect() {
        Integer seven = 7;
        Integer five = 5;
        assertThat(seven, is(greaterThanOrEqualTo(five)));
    }

    @Test
    public void given5_whenGreaterThanOrEqualTo5_thenCorrect() {
        Integer five = 5;
        assertThat(five, is(greaterThanOrEqualTo(five)));
    }

    @Test
    public void given3_whenLessThan5_thenCorrect() {
        Integer three = 3;
        Integer five = 5;
        assertThat(three, is(lessThan(five)));
    }

    @Test
    public void given3_whenLessThanOrEqualTo5_thenCorrect() {
        Integer three = 3;
        Integer five = 5;
        assertThat(three, is(lessThanOrEqualTo(five)));
    }

    @Test
    public void given5_whenLessThanOrEqualTo5_thenCorrect() {
        Integer five = 5;
        assertThat(five, is(lessThanOrEqualTo(five)));
    }

    @Test
    public void givenBenjamin_whenGreaterThanAmanda_thenCorrect() {
        String amanda = "Amanda";
        String benjamin = "Benjamin";
        assertThat(benjamin, is(greaterThan(amanda)));
    }

    @Test
    public void givenAmanda_whenLessThanBenajmin_thenCorrect() {
        String amanda = "Amanda";
        String benjamin = "Benjamin";
        assertThat(amanda, is(lessThan(benjamin)));
    }

    @Test
    public void givenToday_whenGreaterThanYesterday_thenCorrect() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        assertThat(today, is(greaterThan(yesterday)));
    }

    @Test
    public void givenToday_whenLessThanTomorrow_thenCorrect() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        assertThat(today, is(lessThan(tomorrow)));
    }

    @Test
    public void givenAmanda_whenOlderThanBenjamin_thenCorrect() {
        Person amanda = new Person("Amanda", 20);
        Person benjamin = new Person("Benjamin", 18);
        assertThat(amanda, is(greaterThan(benjamin)));
    }

    @Test
    public void givenBenjamin_whenYoungerThanAmanda_thenCorrect() {
        Person amanda = new Person("Amanda", 20);
        Person benjamin = new Person("Benjamin", 18);
        assertThat(benjamin, is(lessThan(amanda)));
    }

    class Person implements Comparable<Person> {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public int compareTo(Person o) {
            if (this.age == o.getAge()) return 0;
            if (this.age > o.age) return 1;
            else return -1;
        }
    }

    @Test
    public void givenNaN_whenIsNotANumber_thenCorrect() {
        double zero = 0d;
        assertThat(zero / zero, is(notANumber()));
    }
}
