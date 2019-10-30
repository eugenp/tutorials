package com.baeldung.crawler4j;

import java.io.File;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class HtmlCrawlerController {

    public static void main(String[] args) throws Exception {
        File crawlStorage = new File("src/test/resources/crawler4j");
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());
        config.setMaxDepthOfCrawling(2);

        int numCrawlers = 12;

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://www.baeldung.com/");

        CrawlerStatistics stats = new CrawlerStatistics();
        CrawlController.WebCrawlerFactory<HtmlCrawler> factory = () -> new HtmlCrawler(stats);

        controller.start(factory, numCrawlers);
        System.out.printf("Crawled %d pages %n", stats.getProcessedPageCount());
        System.out.printf("Total Number of outbound links = %d %n", stats.getTotalLinksCount());
    }

}
