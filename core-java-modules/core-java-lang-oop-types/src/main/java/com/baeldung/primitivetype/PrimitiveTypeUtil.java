package com.baeldung.primitivetype;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

import com.google.common.primitives.Primitives;

public class PrimitiveTypeUtil {

    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
    static {
        WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(16);
        WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        WRAPPER_TYPE_MAP.put(Character.class, char.class);
        WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        WRAPPER_TYPE_MAP.put(Double.class, double.class);
        WRAPPER_TYPE_MAP.put(Float.class, float.class);
        WRAPPER_TYPE_MAP.put(Long.class, long.class);
        WRAPPER_TYPE_MAP.put(Short.class, short.class);
        WRAPPER_TYPE_MAP.put(Void.class, void.class);
    }

    public boolean isPrimitiveTypeByCommonsLang(Object source) {
        return ClassUtils.isPrimitiveOrWrapper(source.getClass());
    }

    public boolean isPrimitiveTypeByGuava(Object source) {
        return Primitives.isWrapperType(source.getClass());
    }

    public boolean isPrimitiveType(Object source) {
        return WRAPPER_TYPE_MAP.containsKey(source.getClass());
    }

}
