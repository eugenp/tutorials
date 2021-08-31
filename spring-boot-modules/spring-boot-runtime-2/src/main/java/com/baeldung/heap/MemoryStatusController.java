package com.baeldung.heap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryStatusController {

    @GetMapping("memory-status")
    public MemoryStats getMemoryStatistics() {
        MemoryStats stats = new MemoryStats();
        stats.setHeapSize(Runtime.getRuntime()
            .totalMemory());
        stats.setHeapMaxSize(Runtime.getRuntime()
            .maxMemory());
        stats.setHeapFreeSize(Runtime.getRuntime()
            .freeMemory());
        return stats;
    }
}
