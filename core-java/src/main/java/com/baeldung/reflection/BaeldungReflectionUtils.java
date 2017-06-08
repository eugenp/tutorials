package com.baeldung.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaeldungReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BaeldungReflectionUtils.class);


    public static List<String> getNullPropertiesList(Customer customer) throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(Customer.class, Object.class).getPropertyDescriptors();
        List<PropertyDescriptor> propDescList = Arrays.asList(propDescArr);

        List<String> nullProps = propDescList.stream()
                .filter(nulls(customer))
                .map(PropertyDescriptor::getName)
                .collect(Collectors.toList());
        return nullProps;
    }

    private static Predicate<PropertyDescriptor> nulls(Customer customer) {
        Predicate<PropertyDescriptor> isNull = pd -> {
            Method getterMethod = pd.getReadMethod();
            boolean result = false;
            try {
                result = (getterMethod != null && getterMethod.invoke(customer) == null);
            } catch (Exception e) {
                LOG.error("error invoking getter method");
            }
            return result;
        };
        return isNull;
    }
}
