package com.baeldung.bit.manipulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class BitManipulationUtilsUnitTest {

    @Test
    void givenPositiveInteger_whenFlipAllBits_thenReturnsNegativeComplement() {
        assertThat(BitManipulationUtils.flipAllBits(21)).isEqualTo(-22);
        assertThat(BitManipulationUtils.flipAllBits(0)).isEqualTo(-1);
        assertThat(BitManipulationUtils.flipAllBits(-1)).isEqualTo(0);
    }

    @Test
    void givenPositiveInteger_whenFlipSignificantBits_thenReturnsFlippedValue() {
        assertThat(BitManipulationUtils.flipSignificantBits(21)).isEqualTo(10);
        assertThat(BitManipulationUtils.flipSignificantBits(26)).isEqualTo(5);
        assertThat(BitManipulationUtils.flipSignificantBits(0)).isEqualTo(0);
        assertThat(BitManipulationUtils.flipSignificantBits(1)).isEqualTo(0);
        assertThat(BitManipulationUtils.flipSignificantBits(7)).isEqualTo(0);
    }

    @Test
    void givenPositiveInteger_whenFlipSignificantBitsUsingHighestOneBit_thenReturnsFlippedValue() {
        assertThat(BitManipulationUtils.flipSignificantBitsUsingHighestOneBit(21)).isEqualTo(10);
        assertThat(BitManipulationUtils.flipSignificantBitsUsingHighestOneBit(26)).isEqualTo(5);
        assertThat(BitManipulationUtils.flipSignificantBitsUsingHighestOneBit(0)).isEqualTo(0);
    }

    @Test
    void givenPositiveInteger_whenFlipSignificantBitsUsingNot_thenReturnsFlippedValue() {
        assertThat(BitManipulationUtils.flipSignificantBitsUsingNot(21)).isEqualTo(10);
        assertThat(BitManipulationUtils.flipSignificantBitsUsingNot(26)).isEqualTo(5);
        assertThat(BitManipulationUtils.flipSignificantBitsUsingNot(0)).isEqualTo(0);
    }

    @Test
    void givenInteger_whenFlipBitsAlternative_thenReturnsSameAsNot() {
        assertThat(BitManipulationUtils.flipBitsArithmetic(21)).isEqualTo(~21);
        assertThat(BitManipulationUtils.flipBitsXorMinusOne(21)).isEqualTo(~21);
        assertThat(BitManipulationUtils.flipBitsArithmetic(0)).isEqualTo(~0);
        assertThat(BitManipulationUtils.flipBitsXorMinusOne(0)).isEqualTo(~0);
    }
}
