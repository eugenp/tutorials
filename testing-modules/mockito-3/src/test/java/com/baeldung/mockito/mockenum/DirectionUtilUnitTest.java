package com.baeldung.mockito.mockenum;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class DirectionUtilUnitTest {

    @Test
    void givenMockedDirection_whenGetDescription_thenThrowException() {
        try (MockedStatic<Direction> directionMock = Mockito.mockStatic(Direction.class)) {
            final Direction unsupported = Mockito.mock(Direction.class);
            Mockito.doReturn(4)
              .when(unsupported)
              .ordinal();

            directionMock.when(Direction::values)
              .thenReturn(new Direction[] { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, unsupported });

            assertThrows(IllegalArgumentException.class, () -> DirectionUtils.getDescription(unsupported));
        }
    }

    @Test
    void givenUnknownDirection_whenGetDescription_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> DirectionUtils.getDescription(Direction.UNKNOWN));
    }
}