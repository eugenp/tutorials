package com.baeldung.dirmonitoring;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class DirectoryMonitoringExample {

    public static final int POLL_INTERVAL = 500;

    public static void main(String[] args) throws Exception {
        FileAlterationObserver observer = new FileAlterationObserver(System.getProperty("user.home"));
        FileAlterationMonitor monitor = new FileAlterationMonitor(POLL_INTERVAL);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            @Override
            public void onFileCreate(File file) {
                System.out.println("File: " + file.getName() + " created");
            }

            @Override
            public void onFileDelete(File file) {
                System.out.println("File: " + file.getName() + " deleted");
            }

            @Override
            public void onFileChange(File file) {
                System.out.println("File: " + file.getName() + " changed");
            }
        };
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();

    }
}
