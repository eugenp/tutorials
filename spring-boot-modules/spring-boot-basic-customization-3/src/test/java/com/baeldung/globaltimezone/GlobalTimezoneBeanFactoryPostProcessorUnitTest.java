package com.baeldung.globaltimezone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = MainApplication.class)
class GlobalTimezoneBeanFactoryPostProcessorUnitTest {

    @Autowired
    private GlobalTimeZoneBean globalTimeZoneBean;

    @Test
    void givenUTCTimeZoneSetInBeanFactoryPostProcessor_whenRetrieveGlobalTimeZone_thenReturnUTC() {
        assertThat(globalTimeZoneBean.getGlobalTimeZone()).isEqualTo("GMT+08:00");
    }
}
