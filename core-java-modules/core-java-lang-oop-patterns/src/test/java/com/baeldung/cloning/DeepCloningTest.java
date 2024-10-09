package com.baeldung.cloning;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class DeepCloningTest {

    @Test
    public void whenCloningObject_thenObjectsShouldNotBeTheSame() throws CloneNotSupportedException {
        Country country = new Country("KE", "Kenya", new Currency("KES", "Ksh", BigDecimal.valueOf(1000)));
        Country deepClone = country.clone();

        assertThat(country).isNotSameAs(deepClone);
        assertThat(country.getCurrency()).isNotSameAs(deepClone.getCurrency());

    }

    @Test
    public void whenDeepCloningObjectWithConstructor_thenObjectsShouldNotBeTheSame() throws CloneNotSupportedException {
        Country country = new Country("KE", "Kenya", new Currency("KES", "Ksh", BigDecimal.valueOf(1000)));
        Country deepClone = new Country(country);
        assertThat(country).isNotSameAs(deepClone);
        assertThat(country.getCurrency()).isNotSameAs(deepClone.getCurrency());
    }

    @Test
    public void whenOriginalObjectIsModified_thenObjectsShouldNotBeEqual() throws CloneNotSupportedException {
        Country country = new Country("KE", "Kenya", new Currency("KES", "Ksh", BigDecimal.valueOf(1000)));
        Country deepClone = new Country(country);

        country.getCurrency().setCode("KE");
        assertThat(country).isNotSameAs(deepClone);
        assertThat(country.getCurrency()).isNotSameAs(deepClone.getCurrency());
        assertThat(country.getCurrency().getCode()).isNotEqualTo(deepClone.getCurrency().getCode());
    }
}