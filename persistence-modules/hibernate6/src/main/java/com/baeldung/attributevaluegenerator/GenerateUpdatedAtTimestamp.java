package com.baeldung.attributevaluegenerator;

import org.hibernate.annotations.ValueGenerationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValueGenerationType(generatedBy = UpdatedAtGenerator.class)
public @interface GenerateUpdatedAtTimestamp {
}