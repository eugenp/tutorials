package com.baeldung.jackson.whatsnew;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanNamingTest {

    @Test
    void givenClassWithGetter_whenWriteValueAsString_thenUsesGetterAsJsonPropertyName() {
        var link = new Link("https://baeldung.com");
        var mapper = MapperFactory.getMapper();

        var json = mapper.writeValueAsString(link);

        assertThat(json)
            .isEqualTo("""
                {
                  "theURL" : "https://baeldung.com"
                }""");
    }

    private static class Link {
        private final String address;

        Link(String address) {
            this.address = address;
        }

        public String getTheURL() {
            return address;
        }
    }

}
