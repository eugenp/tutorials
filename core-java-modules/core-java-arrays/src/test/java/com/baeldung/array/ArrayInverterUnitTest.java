package com.baeldung.array;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ArrayInverterUnitTest {

    private String[] fruits = { "apples", "tomatoes", "bananas", "guavas", "pineapples", "oranges" };

    @Test
    public void invertArrayWithForLoop() {
        ArrayInverter inverter = new ArrayInverter();
        inverter.invertUsingFor(fruits);

        assertThat(new String[] { "oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples" }).isEqualTo(fruits);
    }

    @Test
    public void invertArrayWithCollectionsReverse() {
        ArrayInverter inverter = new ArrayInverter();
        inverter.invertUsingCollectionsReverse(fruits);

        assertThat(new String[] { "oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples" }).isEqualTo(fruits);
    }

    @Test
    public void invertArrayWithStreams() {
        ArrayInverter inverter = new ArrayInverter();

        assertThat(new String[] { "oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples" }).isEqualTo(inverter.invertUsingStreams(fruits));
    }

    @Test
    public void invertArrayWithCommonsLang() {
        ArrayInverter inverter = new ArrayInverter();
        inverter.invertUsingCommonsLang(fruits);

        assertThat(new String[] { "oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples" }).isEqualTo(fruits);
    }

    @Test
    public void invertArrayWithGuava() {
        ArrayInverter inverter = new ArrayInverter();

        assertThat(new String[] { "oranges", "pineapples", "guavas", "bananas", "tomatoes", "apples" }).isEqualTo(inverter.invertUsingGuava(fruits));
    }

}
