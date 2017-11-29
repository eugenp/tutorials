package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoDiscoveryManagedBean {

        private AutoDiscoveryServiceA autoDiscoveryServiceA;
        @Autowired
        private AutoDiscoveryServiceB autoDiscoveryServiceB;

        @Autowired
        public AutoDiscoveryManagedBean(AutoDiscoveryServiceA autoDiscoveryServiceA) {
                this.autoDiscoveryServiceA = autoDiscoveryServiceA;
        }

}
