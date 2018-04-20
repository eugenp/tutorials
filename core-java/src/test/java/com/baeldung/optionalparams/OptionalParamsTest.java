package com.baeldung.optionalparams;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class OptionalParamsTest {

    @Test
    public void whenCreateMultiVitaminWithBuilder_thenOk() {
        MultiVitaminWithBuilder vitamin 
            = new MultiVitaminWithBuilder.MultiVitaminBuilder("Maximum Strength")
                .withCalcium(100)
                .withIron(200)
                .withVitaminA(50)
                .withVitaminC(1000)
                .build();
        assertThat(vitamin.getName()).isEqualTo("Maximum Strength");
        assertThat(vitamin.getCalcium()).isEqualTo(100);
        assertThat(vitamin.getIron()).isEqualTo(200);
        assertThat(vitamin.getVitaminA()).isEqualTo(50);
        assertThat(vitamin.getVitaminC()).isEqualTo(1000);
    }

    @Test
    public void whenCreateMultiVitaminWithNulls_thenOk() {
        MultiVitamin vitamin = new MultiVitamin(null);
        assertThat(vitamin.getName()).isNull();
    }
}
