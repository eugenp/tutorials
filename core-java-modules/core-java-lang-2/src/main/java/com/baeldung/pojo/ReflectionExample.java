package com.baeldung.pojo;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;


public class ReflectionExample {

    public static void main(String[] args) {

        System.out.println("Fields for EmployeePojo are:");
        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(EmployeePojo.class)) {
            System.out.println(pd.getDisplayName());
        }

        System.out.println("Fields for EmployeeBean are:");
        for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(EmployeeBean.class)) {
            System.out.println(pd.getDisplayName());
        }

    }

}
