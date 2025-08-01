package com.baeldung.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BookScraper implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        var books = page.getHtml().css("article.product_pod");

        for (int i = 0; i < Math.min(10, books.nodes().size()); i++) {
            var book = books.nodes().get(i);

            String title = book.css("h3 a", "title").get();
            String price = book.css(".price_color", "text").get();

            System.out.println("Title: " + title + " | Price: " + price);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new BookScraper())
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();
    }
}
