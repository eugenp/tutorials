package com.baeldung.crawler4j;

import java.io.File;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class MultipleCrawlerController {
    public static void main(String[] args) throws Exception {
        File crawlStorageBase = new File("src/test/resources/crawler4j");
        CrawlConfig htmlConfig = new CrawlConfig();
        CrawlConfig imageConfig = new CrawlConfig();
        
        htmlConfig.setCrawlStorageFolder(new File(crawlStorageBase, "html").getAbsolutePath());
        imageConfig.setCrawlStorageFolder(new File(crawlStorageBase, "image").getAbsolutePath());
        imageConfig.setIncludeBinaryContentInCrawling(true);
        
        htmlConfig.setMaxPagesToFetch(500);
        imageConfig.setMaxPagesToFetch(1000);
        
        PageFetcher pageFetcherHtml = new PageFetcher(htmlConfig);
        PageFetcher pageFetcherImage = new PageFetcher(imageConfig);
        
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcherHtml);

        CrawlController htmlController = new CrawlController(htmlConfig, pageFetcherHtml, robotstxtServer);
        CrawlController imageController = new CrawlController(imageConfig, pageFetcherImage, robotstxtServer);
        
        htmlController.addSeed("https://www.baeldung.com/");
        imageController.addSeed("https://www.baeldung.com/");
        
        CrawlerStatistics stats = new CrawlerStatistics();
        CrawlController.WebCrawlerFactory<HtmlCrawler> htmlFactory = () -> new HtmlCrawler(stats);
        
        File saveDir = new File("src/test/resources/crawler4j");
        CrawlController.WebCrawlerFactory<ImageCrawler> imageFactory = () -> new ImageCrawler(saveDir);
        
        imageController.startNonBlocking(imageFactory, 7);
        htmlController.startNonBlocking(htmlFactory, 10);
        

        htmlController.waitUntilFinish();
        System.out.printf("Crawled %d pages %n", stats.getProcessedPageCount());
        System.out.printf("Total Number of outbound links = %d %n", stats.getTotalLinksCount());

        imageController.waitUntilFinish();
        System.out.printf("Image Crawler is finished.");
        
    }
}
