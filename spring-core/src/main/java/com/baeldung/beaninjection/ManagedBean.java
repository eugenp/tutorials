package com.baeldung.beaninjection;

public class ManagedBean {

        private ServiceA serviceA;
        private ServiceB serviceB;

        public ManagedBean(ServiceA serviceA) {

                this.serviceA = serviceA;
        }

        public void setServiceB(ServiceB serviceB) {

                this.serviceB = serviceB;
        }

}
