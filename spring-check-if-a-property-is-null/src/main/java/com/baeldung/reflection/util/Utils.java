package com.baeldung.reflection.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baeldung.reflection.model.Customer;

public class Utils {

    public static List<String> getNullPropertiesList(Customer customer) throws Exception {
        PropertyDescriptor[] propDescArr = Introspector.getBeanInfo(Customer.class, Object.class).getPropertyDescriptors();
        List<PropertyDescriptor>  propDescList = Arrays.asList(propDescArr);
        
        List<String> nullProps = new ArrayList<String>();
        
        propDescList.stream().forEach(p -> {
            Method getterMethod = p.getReadMethod();
            try {
                if (getterMethod != null && getterMethod.invoke(customer) == null) {
                    // If the value if null for that field
                    nullProps.add(p.getName());
                }
            } catch (Exception e) {
                // Handle the exception
                e.printStackTrace();
            }
        });
        return nullProps;
    }
}
