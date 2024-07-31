package com.baeldung.jmxshell.custom;

public class JmxInvoker {

    public static void main(String... args) throws Exception {
        String serviceURL = args[0];
        String name = args[1];
        String operation = args[2];
        String attributeValue = null;
        if (args.length > 3) {
            attributeValue = args[3];
        }

        String result = execute(serviceURL, name, operation, attributeValue);
        System.out.println(result);
    }

    public static String execute(String url, String mBeanName, String operation, String attributeValue) {
        try {
            JmxConnectionWrapper connection = new JmxConnectionWrapper(url, mBeanName);

            if (connection.hasAttribute(operation)) {
                Object value = connection.attributeValue(operation, attributeValue);
                return operation + "=" + value;
            } else {
                Object result = connection.invoke(operation);
                return operation + "(): " + result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getClass() + ": " + e.getMessage();
        }
    }
}
