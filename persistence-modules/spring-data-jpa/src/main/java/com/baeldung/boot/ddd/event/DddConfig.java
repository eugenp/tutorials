/**
 *
 */
package com.baeldung.boot.ddd.event;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootConfiguration
@EnableAutoConfiguration
@PropertySource("classpath:/ddd.properties")
public class DddConfig {

}
