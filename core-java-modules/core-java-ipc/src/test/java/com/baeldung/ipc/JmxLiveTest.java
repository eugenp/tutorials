package com.baeldung.ipc;

import org.junit.jupiter.api.Test;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class JmxLiveTest {
    /*
     * This test needs to be run with the following system properties defined:
     * -Dcom.sun.management.jmxremote=true
     * -Dcom.sun.management.jmxremote.port=1234
     * -Dcom.sun.management.jmxremote.authenticate=false
     * -Dcom.sun.management.jmxremote.ssl=false
     */
    @Test
    public void consumer() throws Exception {
        ObjectName objectName = new ObjectName("com.baeldung.ipc:type=basic,name=test");
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(new IPCTest(), objectName);

        TimeUnit.MINUTES.sleep(50);
    }

    @Test
    public void producer() throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1234/jmxrmi");
        try (JMXConnector jmxc = JMXConnectorFactory.connect(url, null)) {
            ObjectName objectName = new ObjectName("com.baeldung.ipc:type=basic,name=test");

            IPCTestMBean mbeanProxy = JMX.newMBeanProxy(jmxc.getMBeanServerConnection(), objectName, IPCTestMBean.class, true);
            mbeanProxy.sendMessage("Hello");
        }
    }

    public interface IPCTestMBean {
        void sendMessage(String message);
    }

    class IPCTest implements IPCTestMBean {
        @Override
        public void sendMessage(String message) {
            System.out.println("Received message: " + message);
        }
    }
}
