package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;

public class AnnotationManagedBean {

        private ServiceA serviceA;
        @Autowired
        private ServiceB serviceB;

        public AnnotationManagedBean(ServiceA serviceA) {

                this.serviceA = serviceA;
        }
        
}
