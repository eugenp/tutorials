package com.baeldung.classorders;

import java.util.Comparator;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

public class CustomClassOrderer implements ClassOrderer {
    @Override
    public void orderClasses(ClassOrdererContext context) {
        context.getClassDescriptors().sort(
            Comparator.comparingInt(descriptor ->
                descriptor.getTestClass().getSimpleName().length()
            )
        );
    }
}
