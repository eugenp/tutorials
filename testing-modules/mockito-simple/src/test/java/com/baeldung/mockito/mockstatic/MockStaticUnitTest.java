package com.baeldung.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.baeldung.mockito.mockedstatic.StaticUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import java.util.Arrays;

class MockStaticUnitTest {

    @Test
    void givenStaticMethodWithNoArgs_whenMocked_thenReturnsMockSuccessfully() {
        assertThat(StaticUtils.name()).isEqualTo("Baeldung");

        try (MockedStatic<StaticUtils> utilities = mockStatic(StaticUtils.class)) {
            utilities.when(StaticUtils::name).thenReturn("Eugen");
            assertThat(StaticUtils.name()).isEqualTo("Eugen");
        }

        assertThat(StaticUtils.name()).isEqualTo("Baeldung");
    }

    @Test
    void givenStaticMethodWithArgs_whenMocked_thenReturnsMockSuccessfully() {
        assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);

        try (MockedStatic<StaticUtils> utilities = mockStatic(StaticUtils.class)) {
            utilities.when(() -> StaticUtils.range(2, 6))
                .thenReturn(Arrays.asList(10, 11, 12));

            assertThat(StaticUtils.range(2, 6)).containsExactly(10, 11, 12);
        }

        assertThat(StaticUtils.range(2, 6)).containsExactly(2, 3, 4, 5);
    }

}
