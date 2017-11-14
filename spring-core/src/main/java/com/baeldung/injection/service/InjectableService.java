package com.baeldung.injection.service;

import org.springframework.stereotype.Service;

@Service
public class InjectableService {
        public String performComplexOperation(String injectionType) {
                return String.format("Operation has been performed with: %s", injectionType);
        }
}
