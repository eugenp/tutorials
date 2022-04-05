package com.baeldung.jupiter;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringJUnit5Config {

    @AliasFor(annotation = ContextConfiguration.class, attribute = "classes")
    Class<?>[] value() default {};

    @AliasFor(annotation = ContextConfiguration.class)
    Class<?>[] classes() default {};

    @AliasFor(annotation = ContextConfiguration.class)
    String[] locations() default {};

    @AliasFor(annotation = ContextConfiguration.class)
    Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>[] initializers() default {};

    @AliasFor(annotation = ContextConfiguration.class)
    boolean inheritLocations() default true;

    @AliasFor(annotation = ContextConfiguration.class)
    boolean inheritInitializers() default true;

    @AliasFor(annotation = ContextConfiguration.class)
    String name() default "";
}
