package com.baeldung.globaltimezone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
class GlobalTimezoneBeanFactoryPostProcessorUnitTest {

    @Autowired
    private GlobalTimeZoneBean globalTimeZoneBean;

    @Test
    void givenUTCTimeZoneSetInBeanFactoryPostProcessor_whenRetrieveGlobalTimeZone_thenReturnUTC() {
        assertThat(globalTimeZoneBean.getGlobalTimeZone()).isEqualTo("Coordinated Universal Time");
    }
}
