package com.baeldung.threaddump;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ThreadDump {
    
    public static void main(String[] args) throws IOException {
        threadDump(true, true);
    }
    
    private static void threadDump(boolean lockedMonitors, boolean lockedSynchronizers) throws IOException {
        Path threadDumpFile = Paths.get("ThreadDump.txt");
        
        StringBuffer threadDump = new StringBuffer(System.lineSeparator());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        for(ThreadInfo threadInfo : threadMXBean.dumpAllThreads(lockedMonitors, lockedSynchronizers)) {
            threadDump.append(threadInfo.toString());
        }
        Files.write(threadDumpFile, threadDump.toString().getBytes());
    }

}
