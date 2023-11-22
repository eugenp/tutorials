package com.baeldung.deserializationfilters;

import java.io.ObjectInputFilter;
import java.util.function.BinaryOperator;

import com.baeldung.deserializationfilters.service.DeserializationService;
import com.baeldung.deserializationfilters.service.LimitedArrayService;
import com.baeldung.deserializationfilters.service.LowDepthService;
import com.baeldung.deserializationfilters.service.SmallObjectService;
import com.baeldung.deserializationfilters.utils.FilterUtils;

public class ContextSpecificDeserializationFilterFactory implements BinaryOperator<ObjectInputFilter> {

    @Override
    public ObjectInputFilter apply(ObjectInputFilter current, ObjectInputFilter next) {
        if (current == null) {
            Class<?> caller = findInStack(DeserializationService.class);

            if (caller == null) {
                current = FilterUtils.fallbackFilter();
            } else if (caller.equals(SmallObjectService.class)) {
                current = FilterUtils.safeSizeFilter(190);
            } else if (caller.equals(LowDepthService.class)) {
                current = FilterUtils.safeDepthFilter(2);
            } else if (caller.equals(LimitedArrayService.class)) {
                current = FilterUtils.safeArrayFilter(3);
            }
        }

        return ObjectInputFilter.merge(current, next);
    }

    private static Class<?> findInStack(Class<?> superType) {
        for (StackTraceElement element : Thread.currentThread()
            .getStackTrace()) {
            try {
                Class<?> subType = Class.forName(element.getClassName());
                if (superType.isAssignableFrom(subType)) {
                    return subType;
                }
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        return null;
    }
}
