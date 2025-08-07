package com.baeldung.crawler4j;

public class CrawlerStatistics {
    private int processedPageCount = 0;
    private int totalLinksCount = 0;
    
    public void incrementProcessedPageCount() {
        processedPageCount++;
    }
    
    public void incrementTotalLinksCount(int linksCount) {
        totalLinksCount += linksCount;
    }
    
    public int getProcessedPageCount() {
        return processedPageCount;
    }
    
    public int getTotalLinksCount() {
        return totalLinksCount;
    }
}
