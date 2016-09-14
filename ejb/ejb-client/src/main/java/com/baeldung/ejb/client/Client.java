package com.baeldung.ejb.client;

import javax.naming.Context;
import javax.naming.NamingException;
import com.baeldung.ejb.tutorial.HelloWorldRemote;

public class Client {
    // public static void main(String[] args) {
    // HelloWorldRemote bean = doLookup();
    // System.out.println(bean.getHelloWorld()); // 4. Call business logic
    // }
    //
    public HelloWorldRemote doLookup() {
        Context context = null;
        HelloWorldRemote bean = null;
        try {
            // 1. Obtaining Context
            context = ClientUtility.getInitialContext();
            // 2. Generate JNDI Lookup name
            String lookupName = getLookupName();
            // 3. Lookup and cast
            bean = (HelloWorldRemote) context.lookup(lookupName);

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }

    private static String getLookupName() {
        /*
         * The app name is the EAR name of the deployed EJB without .ear suffix.
         * Since we haven't deployed the application as a .ear, the app name for
         * us will be an empty string
         */
        String appName = "";

        /*
         * The module name is the JAR name of the deployed EJB without the .jar
         * suffix.
         */
        String moduleName = "ejb-remote-0.0.1-SNAPSHOT";

        /*
         * AS7 allows each deployment to have an (optional) distinct name. This
         * can be an empty string if distinct name is not specified.
         */
        String distinctName = "";

        // The EJB bean implementation class name
        String beanName = "HelloWorldBean";

        // Fully qualified remote interface name
        final String interfaceName = "com.baeldung.ejb.tutorial.HelloWorldRemote";

        // Create a look up string name
        String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + interfaceName;

        // String name = "ejbpersistence-0.0.1-SNAPSHOT.jar";

        return name;
    }

}