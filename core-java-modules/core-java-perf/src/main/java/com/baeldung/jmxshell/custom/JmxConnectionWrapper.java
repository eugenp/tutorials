package com.baeldung.jmxshell.custom;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxConnectionWrapper {

    private final Map<String, MBeanAttributeInfo> attributeMap;
    private final MBeanServerConnection connection;
    private final ObjectName objectName;

    public JmxConnectionWrapper(String url, String beanName) throws Exception {
        objectName = new ObjectName(beanName);

        connection = JMXConnectorFactory.connect(new JMXServiceURL(url))
            .getMBeanServerConnection();

        MBeanInfo bean = connection.getMBeanInfo(objectName);
        MBeanAttributeInfo[] attributes = bean.getAttributes();

        this.attributeMap = Stream.of(attributes)
            .peek(System.out::println)
            .collect(Collectors.toMap(MBeanAttributeInfo::getName, Function.identity()));
    }

    public boolean hasAttribute(String attributeName) {
        return attributeMap.containsKey(attributeName);
    }

    public Object attributeValue(String name, String value) throws Exception {
        if (value != null) {
            connection.setAttribute(objectName, new Attribute(name, parse(value)));
        }
        return connection.getAttribute(objectName, name);
    }

    public Object invoke(String operation) throws Exception {
        String[] signature = new String[] {};
        Object[] params = new Object[] {};

        return connection.invoke(objectName, operation, params, signature);
    }

    private static Object parse(String value) {
        if (value == null)
            return null;

        if (value.matches("\\d+")) {
            return Integer.valueOf(value);
        } else if (value.trim()
            .toLowerCase()
            .matches("true|false")) {
            return Boolean.valueOf(value);
        }

        return value.equals("null") ? null : value;
    }
}
