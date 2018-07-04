package com.baeldung.reactive.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import com.baeldung.reactive.Spring5ReactiveApplication;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class CpuUtilsTest {

    @Test
    public void whenGetUsage_returnCorrectValue() {
        Double usage = CpuUtils.getUsage();
        assertNotNull(usage);
        assertThat(usage, Matchers.lessThanOrEqualTo(100d));
    }

}
