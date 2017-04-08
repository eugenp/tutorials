/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public static SynthesizingMethodParameter createSynthesizingMethodParameter(
      Parameter parameter) {
        Assert.notNull(parameter, "Parameter must not be null");
        Executable executable = parameter.getDeclaringExecutable();
        if (executable instanceof Method) {
            return new SynthesizingMethodParameter((Method) executable
              , getIndex(parameter));
        }
        throw new UnsupportedOperationException(
          "Cannot create a SynthesizingMethodParameter for a constructor parameter: "
          + parameter);
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
        throw new IllegalStateException(String.format(
          "Failed to resolve index of parameter [%s] in executable [%s]",
          parameter, executable.toGenericString()));
    }
}
