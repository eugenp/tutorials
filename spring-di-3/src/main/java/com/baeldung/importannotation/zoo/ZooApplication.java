package com.baeldung.importannotation.zoo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.baeldung.importannotation.animal.AnimalScanConfiguration;

@Configuration
@Import(AnimalScanConfiguration.class)
class ZooApplication {
}
