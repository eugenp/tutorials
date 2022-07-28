package com.baeldung.sample.control;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class bundles all classes in the control layer.
 * This includes also the generated mapper classes.
 */
@TestConfiguration
@ComponentScan(basePackageClasses = TodosControlLayer.class)
public class TodosControlLayer {
}
