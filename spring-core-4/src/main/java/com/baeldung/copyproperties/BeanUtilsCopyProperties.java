package com.baeldung.copyproperties;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Set;

public class BeanUtilsCopyProperties {
    public static void copySelectedPropertiesUsingCustomWrapper(Object source, Object target, Set<String> props) {
        String[] excludedProperties = Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
                .map(PropertyDescriptor::getName)
                .filter(name -> !props.contains(name))
                .toArray(String[]::new);

        BeanUtils.copyProperties(source, target, excludedProperties);
    }

}
