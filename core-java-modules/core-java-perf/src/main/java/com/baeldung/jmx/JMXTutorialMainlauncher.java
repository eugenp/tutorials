package com.baeldung.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class JMXTutorialMainlauncher {

    private static final Logger LOG = LoggerFactory.getLogger(JMXTutorialMainlauncher.class);

    public static void main(String[] args) {

        LOG.debug("This is basic JMX tutorial");

        try {
            ObjectName objectName = new ObjectName("com.baeldung.tutorial:type=basic,name=game");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new Game(), objectName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException |
                MBeanRegistrationException | NotCompliantMBeanException e) {
            e.printStackTrace();
        }

        LOG.debug("Registration for Game mbean with the platform server is successfull");
        LOG.debug("Please open jconsole to access Game mbean");

        while (true) {
            // to ensure application does not terminate
        }
    }

}
