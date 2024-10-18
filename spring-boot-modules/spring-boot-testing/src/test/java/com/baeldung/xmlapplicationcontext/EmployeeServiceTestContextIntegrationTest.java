package com.baeldung.xmlapplicationcontext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.xmlapplicationcontext.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XmlBeanApplication.class)
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
