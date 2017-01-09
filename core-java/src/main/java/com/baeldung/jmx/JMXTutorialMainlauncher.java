package com.baeldung.jmx;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class JMXTutorialMainlauncher {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        System.out.println("This is basic JMX tutorial");
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("com.baeldung.tutorial:type=basic,name=game");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Game gameObj = new Game();
        try {
            server.registerMBean(gameObj, objectName);
        } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }
        System.out.println("Registration for Game mbean with the platform server is successfull");
        System.out.println("Please open jconsole to access Game mbean");
        while (true) {
            // to ensure application does not terminate
        }
    }

}
