package com.baeldung.concatenate

import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class WonderUnitTest {

    static final String EXPECTED_SINGLE_LINE = "The seven wonders of the world"

    Wonder wonder

    @Before
    void before() {
        wonder = new Wonder()
    }

    @Test
    void whenUsingOperatorPlus_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.operator_plus())
    }

    @Test
    void whenUsingOperatorLeft_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.operator_left())
    }

    @Test
    void whenUsingInterpolationOne_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.interpolation_one())
    }

    @Test
    void whenUsingInterpolationTwo_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.interpolation_two())
    }

    @Test
    void whenUsingMultiline_thenConcatCorrectly() {
        def expectedMultiline = """
            There are seven wonders of the world.
            Can you name them all? 
            1. The Great Pyramid of Giza
            2. Hanging Gardens of Babylon
            3. Colossus of Rhode
            4. Lighthouse of Alexendra
            5. Temple of Artemis
            6. Status of Zeus at Olympia
            7. Mausoleum at Halicarnassus
        """
        assertEquals(expectedMultiline, wonder.multilineString())
    }

    @Test
    void whenUsingMethodConcat_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.method_concat())
    }

    @Test
    void whenUsingMethodBuilder_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.method_builder())
    }

    @Test
    void whenUsingMethodBuffer_thenConcatCorrectly() {
        assertEquals(EXPECTED_SINGLE_LINE, wonder.method_buffer())
    }
}
