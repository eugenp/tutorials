package com.baeldung.collections.iterators;

import static com.baeldung.collections.iterators.Iterators.failFast1;
import static com.baeldung.collections.iterators.Iterators.failFast2;
import static com.baeldung.collections.iterators.Iterators.failSafe1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ConcurrentModificationException;

import org.junit.Test;

/**
 * Source code https://github.com/eugenp/tutorials
 *
 * @author Santosh Thakur
 */

public class IteratorsUnitTest {

    @Test
    public void whenFailFast_ThenThrowsException() {
        assertThatThrownBy(() -> {
            failFast1();
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    public void whenFailFast_ThenThrowsExceptionInSecondIteration() {
        assertThatThrownBy(() -> {
            failFast2();
        }).isInstanceOf(ConcurrentModificationException.class);
    }

    @Test
    public void whenFailSafe_ThenDoesNotThrowException() {
        assertThat(failSafe1()).isGreaterThanOrEqualTo(0);
    }

}
