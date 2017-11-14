package com.baeldung.injection.type;

import com.baeldung.injection.service.InjectableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorInjectionComponent {

        private InjectableService injectableService;

        @Autowired
        public ConstructorInjectionComponent(InjectableService injectableService) {
                this.injectableService = injectableService;
        }

        public String reuseService() {
                return injectableService.performComplexOperation("constructor injection");
        }
}
