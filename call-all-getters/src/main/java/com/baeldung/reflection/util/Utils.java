package com.baeldung.reflection.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.baeldung.reflection.model.Customer;

public class Utils {

    public static List<String> getNullPropertiesList(Customer customer) throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(Customer.class, Object.class).getPropertyDescriptors();
        List<PropertyDescriptor>  propDescList = Arrays.asList(propDescArr);
        
        List<String> nullProps = propDescList.stream()
                                        .filter(nulls(customer))
                                        .map(PropertyDescriptor::getName)
                                        .collect(Collectors.toList());
        return nullProps;
    }

    private static Predicate<PropertyDescriptor> nulls(Customer customer) {
        Predicate<PropertyDescriptor> isNull = new Predicate<PropertyDescriptor>() {
            @Override
            public boolean test(PropertyDescriptor pd) {
                Method getterMethod = pd.getReadMethod();
                boolean result = false;
                try {
                    result = (getterMethod != null && getterMethod.invoke(customer) == null);
                } catch (Exception e) {
                    // Handle the exception
                    e.printStackTrace();
                }
                return result;
            }
        };
        return isNull;
    }
}
