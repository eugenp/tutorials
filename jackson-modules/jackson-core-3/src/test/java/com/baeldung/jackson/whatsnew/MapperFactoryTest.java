package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.SerializationFeature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class MapperFactoryTest {

    @Test
    void whenGetMapper_thenSuccess() {
        assertThatNoException().isThrownBy(MapperFactory::getMapper);
    }

    @Test
    void givenMapper_whenRebuildPretty_thenSuccess() {
        var mapper = MapperFactory.getMapper();

        var prettyMapper = MapperFactory.rebuildPretty(mapper);

        assertThat(prettyMapper)
            .isNotNull()
            .isNotSameAs(mapper)
            .returns(true, m -> m.isEnabled(SerializationFeature.INDENT_OUTPUT));
    }
}