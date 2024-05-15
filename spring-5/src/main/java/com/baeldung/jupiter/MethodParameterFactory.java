package com.baeldung.jupiter;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

abstract class MethodParameterFactory {

    private MethodParameterFactory() {
    }

    public static MethodParameter createMethodParameter(Parameter parameter) {
        Assert.notNull(parameter, "Parameter must not be null");
        Executable executable = parameter.getDeclaringExecutable();
        if (executable instanceof Method) {
            return new MethodParameter((Method) executable, getIndex(parameter));
        }
        return new MethodParameter((Constructor<?>) executable, getIndex(parameter));
    }

    public static SynthesizingMethodParameter createSynthesizingMethodParameter(Parameter parameter) {
        Assert.notNull(parameter, "Parameter must not be null");
        Executable executable = parameter.getDeclaringExecutable();
        if (executable instanceof Method) {
            return new SynthesizingMethodParameter((Method) executable, getIndex(parameter));
        }
        throw new UnsupportedOperationException("Cannot create a SynthesizingMethodParameter for a constructor parameter: " + parameter);
    }

    private static int getIndex(Parameter parameter) {
        Assert.notNull(parameter, "Parameter must not be null");
        Executable executable = parameter.getDeclaringExecutable();
        Parameter[] parameters = executable.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] == parameter) {
                return i;
            }
        }
        throw new IllegalStateException(String.format("Failed to resolve index of parameter [%s] in executable [%s]", parameter, executable.toGenericString()));
    }
}
