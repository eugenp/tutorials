package com.baeldung.defaultparams;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;


public class TeaUnitTest {
    
    @Test
    public void whenTeaWithOnlyName_thenCreateDefaultTea() {
        Tea blackTea = new Tea("Black Tea");

        assertThat(blackTea.getName()).isEqualTo("Black Tea");
        assertThat(blackTea.getMilk()).isEqualTo(0);
        assertThat(blackTea.isHerbs()).isFalse();
        assertThat(blackTea.getSugar()).isEqualTo(0);
        assertThat(blackTea.getTeaPowder()).isEqualTo(Tea.DEFAULT_TEA_POWDER);
    }

}
