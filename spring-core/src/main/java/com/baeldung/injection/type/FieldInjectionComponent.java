package com.baeldung.injection.type;

import com.baeldung.injection.service.InjectableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectionComponent {

        @Autowired
        private InjectableService injectableService;

        public String reuseService() {
                return injectableService.performComplexOperation("field injection");
        }
}
