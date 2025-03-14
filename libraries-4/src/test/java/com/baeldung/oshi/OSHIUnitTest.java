package com.baeldung.oshi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.software.os.OperatingSystem;

import org.junit.jupiter.api.Test;

class OSHIUnitTest {

    @Test
    void givenSystem_whenUsingOSHI_thenExtractOSDetails() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        assertNotNull(os, "Operating System object should not be null");
        assertNotNull(os.getFamily(), "OS Family should not be null");
        assertNotNull(os.getVersionInfo(), "OS Version info should not be null");
        assertTrue(os.getBitness() == 32 || os.getBitness() == 64, "OS Bitness should be 32 or 64");
    }

    @Test
    void givenSystem_whenUsingOSHI_thenExtractSystemUptime() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        long uptime = os.getSystemUptime();
        assertTrue(uptime >= 0, "System uptime should be non-negative");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            fail("Test interrupted");
        }
        long newUptime = os.getSystemUptime();
        assertTrue(newUptime >= uptime, "Uptime should increase over time");
    }

    @Test
    void givenSystem_whenUsingOSHI_thenExtractCPUDetails() {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware()
            .getProcessor();

        assertNotNull(processor, "Processor object should not be null");
        assertTrue(processor.getPhysicalProcessorCount() > 0, "CPU must have at least one physical core");
        assertTrue(processor.getLogicalProcessorCount() >= processor.getPhysicalProcessorCount(),
            "Logical cores should be greater than or equal to physical cores");
    }

    @Test
    void givenSystem_whenUsingOSHI_thenExtractCPULoad() throws InterruptedException {
        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware()
            .getProcessor();

        long[] prevTicks = processor.getSystemCpuLoadTicks();
        TimeUnit.SECONDS.sleep(1);
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;

        assertTrue(cpuLoad >= 0 && cpuLoad <= 100, "CPU load should be between 0% and 100%");
    }

    @Test
    void givenSystem_whenUsingOSHI_thenExtractMemoryDetails() {
        SystemInfo si = new SystemInfo();
        GlobalMemory memory = si.getHardware()
            .getMemory();

        assertTrue(memory.getTotal() > 0, "Total memory should be positive");
        assertTrue(memory.getAvailable() >= 0, "Available memory should not be negative");
        assertTrue(memory.getAvailable() <= memory.getTotal(), "Available memory should not exceed total memory");
    }

    @Test
    void givenSystem_whenUsingOSHI_thenExtractDiskDetails() {
        SystemInfo si = new SystemInfo();
        List<HWDiskStore> diskStores = si.getHardware()
            .getDiskStores();

        assertFalse(diskStores.isEmpty(), "There should be at least one disk");

        for (HWDiskStore disk : diskStores) {
            assertNotNull(disk.getModel(), "Disk model should not be null");
            assertTrue(disk.getSize() >= 0, "Disk size should be non-negative");
        }
    }
}
