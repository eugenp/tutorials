package com.baeldung.configuration;

import com.baeldung.beaninjectiontypes.constructor.Library;
import com.baeldung.beaninjectiontypes.constructor.Manager;
import com.baeldung.beaninjectiontypes.setter.Hospital;
import com.baeldung.beaninjectiontypes.setter.Patient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextTestBeanInjection {

    @Bean
    public Library libraryBean() {
        return new Library(managerBean());
    }

    @Bean
    public Manager managerBean() {
        Manager manager = new Manager();
        manager.setName("Stacy");
        return manager;
    }

    @Bean
    public Hospital hospitalBean() {
        Hospital hospital = new Hospital();
        hospital.setPatient(patientBean());
        return hospital;
    }

    @Bean
    public Patient patientBean() {
        Patient patient = new Patient();
        patient.setFirstName("John");
        return patient;
    }
}
