package com.baeldung.enabledif;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.junit.jupiter.EnabledIf;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@EnabledIf(
    expression = "#{systemProperties['java.version'].startsWith('1.8')}",
    reason = "Enabled on Java 8"
)
public @interface EnabledOnJava8 {

}
