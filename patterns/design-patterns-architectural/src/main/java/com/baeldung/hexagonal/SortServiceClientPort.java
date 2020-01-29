package com.baeldung.hexagonal;

public abstract class SortServiceClientPort {
    private SortService sortService;
    
    public SortServiceClientPort(SortService sortService) {
        this.sortService = sortService;
    }
    
    protected SortService getService() {
        return sortService;
    }
}
