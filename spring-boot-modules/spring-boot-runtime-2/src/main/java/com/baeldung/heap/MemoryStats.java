package com.baeldung.heap;

public class MemoryStats {
    private long heapSize;
    private long heapMaxSize;
    private long heapFreeSize;
    
    public long getHeapSize() {
        return heapSize;
    }
    
    public void setHeapSize(long heapSize) {
        this.heapSize = heapSize;
    }
    
    public long getHeapMaxSize() {
        return heapMaxSize;
    }
    
    public void setHeapMaxSize(long heapMaxSize) {
        this.heapMaxSize = heapMaxSize;
    }
    
    public long getHeapFreeSize() {
        return heapFreeSize;
    }
    
    public void setHeapFreeSize(long heapFreeSize) {
        this.heapFreeSize = heapFreeSize;
    }
}
