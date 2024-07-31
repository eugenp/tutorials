package com.baeldung.sample.boundary;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class bundles all classes in the boundary layer.
 * This includes also the generated mapper classes.
 */
@TestConfiguration
@ComponentScan(basePackageClasses = TodosBoundaryLayer.class)
public class TodosBoundaryLayer {
}
