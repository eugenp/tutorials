package com.baeldung.service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class OSHIExample {
    public static void main(String[] args) throws InterruptedException {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        // OS Info
        System.out.println("Operating System: " + os);

        // CPU Usage
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1); // Wait to measure CPU load
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        System.out.printf("CPU Load: %.2f%%\n", cpuLoad);

        // Memory Usage
        GlobalMemory memory = hal.getMemory();
        System.out.printf("Total Memory: %.2f GB\n", memory.getTotal() / 1e9);
        System.out.printf("Available Memory: %.2f GB\n", memory.getAvailable() / 1e9);

        // Disk Space
        List<HWDiskStore> diskStores = hal.getDiskStores();
        for (HWDiskStore disk : diskStores) {
            System.out.println("Disk: " + disk.getModel());
            System.out.printf("Total Space: %.2f GB\n", disk.getSize() / 1e9);
        }
    }
}