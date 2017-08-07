package com.baeldung.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class JMXTutorialMainlauncher {

    private static final Logger LOG = LoggerFactory.getLogger(JMXTutorialMainlauncher.class);

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        LOG.debug("This is basic JMX tutorial");
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
        LOG.debug("Registration for Game mbean with the platform server is successfull");
        LOG.debug("Please open jconsole to access Game mbean");
        while (true) {
            // to ensure application does not terminate
        }
    }

}
