package com.baeldung.mockprotected;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MoviesUnitTest {

    @Test
    void givenProtectedMethod_whenMethodIsVisible_thenUseMockitoToStub() {
        Movies matrix = Mockito.spy(new Movies("The Matrix"));
        assertThat(matrix.getPlaceHolder()).isEqualTo("Movie: The Matrix");

        doReturn("something else").when(matrix)
            .getTitle();

        assertThat(matrix.getTitle()).isEqualTo("something else");
        assertThat(matrix.getPlaceHolder()).isEqualTo("Movie: something else");
    }
}
