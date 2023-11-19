package com.baeldung.jmxshell;

import java.lang.management.ManagementFactory;
import java.util.Scanner;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.baeldung.jmxshell.bean.Calculator;

public class JmxCalculatorMain {

    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(new Calculator(), new ObjectName("com.baeldung.jxmshell:type=basic,name=calculator"));

        System.out.printf("mbean registered. pid: %s\n", ManagementFactory.getRuntimeMXBean()
            .getName());

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("<press enter to terminate>");
            scanner.nextLine();
        }
    }
}
