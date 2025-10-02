package com.baeldung.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookScraper implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    private final List<Book> books = new ArrayList<>();

    @Override
    public void process(Page page) {
        var bookNodes = page.getHtml().css("article.product_pod");

        for (int i = 0; i < Math.min(10, bookNodes.nodes().size()); i++) {
            var book = bookNodes.nodes().get(i);

            String title = book.css("h3 a", "title").get();
            String price = book.css(".price_color", "text").get();

            books.add(new Book(title, price));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public static void main(String[] args) {
        BookScraper bookScraper = new BookScraper();
        Spider.create(bookScraper)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        bookScraper.getBooks().forEach(book ->
                System.out.println("Title: " + book.getTitle() + " | Price: " + book.getPrice()));
    }
}
