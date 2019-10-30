package com.baeldung.commons.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileMonitor {

    public static void main(String[] args) throws Exception {
        File folder = FileUtils.getTempDirectory();
        startFileMonitor(folder);
    }

    /**
     * @param folder
     * @throws Exception
     */
    public static void startFileMonitor(File folder) throws Exception {
        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor = new FileAlterationMonitor(5000);

        FileAlterationListener fal = new FileAlterationListenerAdaptor() {

            @Override
            public void onFileCreate(File file) {
                // on create action
            }

            @Override
            public void onFileDelete(File file) {
                // on delete action
            }
        };

        observer.addListener(fal);
        monitor.addObserver(observer);
        monitor.start();
    }
}