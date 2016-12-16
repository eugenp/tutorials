package com.baeldung.jacksonannotation.miscellaneous.custom;

import com.fasterxml.jackson.annotation.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"title", "price", "id", "duration", "authors", "level"})
@JsonIgnoreProperties({"prerequisite"})
public @interface CustomCourseAnnotation {
}
