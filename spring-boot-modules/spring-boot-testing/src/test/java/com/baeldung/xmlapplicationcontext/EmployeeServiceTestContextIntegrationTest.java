package com.baeldung.xmlapplicationcontext;

import com.baeldung.xmlapplicationcontext.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(locations = "/test-context.xml")
public class EmployeeServiceTestContextIntegrationTest {

    @Autowired
    @Qualifier("employeeServiceTestImpl")
    private EmployeeService serviceTest;

    @Test
    public void whenTestContextLoads_thenServiceTestISNotNull() {
        assertThat(serviceTest).isNotNull();
    }

}
