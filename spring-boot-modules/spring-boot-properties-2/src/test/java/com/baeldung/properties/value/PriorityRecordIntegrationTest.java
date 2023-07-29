package com.baeldung.properties.value;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriorityRecord.class)
public class PriorityRecordIntegrationTest {

    @Autowired
    private PriorityRecord priorityRecord;

    @Test
    public void givenPropertyFile_WhenConstructorInjectionUsedInRecord_ThenValueInjected() {
        assertThat(priorityRecord.priority()).isEqualTo("high");
    }

}
