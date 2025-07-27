package com.java.baeldung.spring.test;

import com.github.seregamorph.testsmartcontext.SmartDirtiesContextTestExecutionListener;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// Avoid putting @DirtiesContext on the integration test super class
// @DirtiesContext
// Use SmartDirtiesContextTestExecutionListener instead
@ContextConfiguration(classes = {
        SampleBeanTestConfiguration.class
})
@ActiveProfiles("test")
@TestExecutionListeners(listeners = {
        SmartDirtiesContextTestExecutionListener.class,
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@ExtendWith(SpringExtension.class)
public abstract class AbstractIntegrationTest {

}
