package com.baeldung.cloning;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class ShallowCopyTest {

    @Test
    void whenCloningObject_thenObjectsShouldNotBeEqual() throws CloneNotSupportedException {
        Country country = new Country("KE", "Kenya", new Currency("KES", "Ksh", BigDecimal.valueOf(1000)));
        Country shallowClone = new Country(country.getCode(), country.getName(), country.getCurrency());

        assertThat(country).isNotSameAs(shallowClone);
        assertThat(country.getCurrency()).isSameAs(shallowClone.getCurrency());
        assertThat(country.getCode()).isSameAs(shallowClone.getCode());
    }

    @Test
    void whenOriginalObjectIsModified_thenObjectsShouldBeEqual() throws CloneNotSupportedException {
        Country country = new Country("KE", "Kenya", new Currency("KES", "Ksh", BigDecimal.valueOf(1000)));
        Country shallowClone = new Country(country.getCode(), country.getName(), country.getCurrency());

        country.getCurrency()
            .setCode("KE");
        assertThat(country).isNotSameAs(shallowClone);
        assertThat(country.getCurrency()).isSameAs(shallowClone.getCurrency());
        assertThat(country.getCurrency()
            .getCode()).isEqualTo(shallowClone.getCurrency()
            .getCode());
        country.setCode("KEN");
        assertThat(country.getCode()).isNotEqualTo(shallowClone.getCode());
    }

}
