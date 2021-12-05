package com.baeldung.xmlapplicationcontext;

import com.baeldung.xmlapplicationcontext.service.EmployeeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = XmlBeanApplication.class)
public class EmployeeServiceAppContextIntegrationTest {

    @Autowired
    private EmployeeService service;

    @Test
    public void whenContextLoads_thenServiceISNotNull() {
        assertThat(service).isNotNull();
    }

}
