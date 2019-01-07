package com.baeldung.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 反射工具类
 * @author zn.wang
 */
public class BaeldungReflectionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BaeldungReflectionUtils.class);

    public static List<String> getNullPropertiesList(Customer customer) throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(Customer.class, Object.class).getPropertyDescriptors();

        List<String> list = new ArrayList<>();
        Predicate<PropertyDescriptor> predicate = nulls(customer);
        for (PropertyDescriptor propertyDescriptor : propDescArr) {
            if (predicate.test(propertyDescriptor)) {
                String name = propertyDescriptor.getName();
                list.add(name);
            }
        }
        return list;
    }

    private static Predicate<PropertyDescriptor> nulls(Customer customer) {
        return new Predicate<PropertyDescriptor>() {
            @Override
            public boolean test(PropertyDescriptor pd) {
                boolean result = false;
                try {
                    Method getterMethod = pd.getReadMethod();
                    result = (getterMethod != null && getterMethod.invoke(customer) == null);
                } catch (Exception e) {
                    LOG.error("error invoking getter method");
                }
                return result;
            }
        };
    }
}
