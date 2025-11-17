package com.baeldung.webmagic;

import us.codecraft.webmagic.Spider;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookScraperLiveTest {

    @Test
    void whenScrapeABookSite_thenShouldReturnTitleAndPrice() {
        BookScraper scraper = new BookScraper();

        Spider.create(scraper)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        List<Book> books = scraper.getBooks();

        assertFalse(books.isEmpty(), "Expected to scrape at least one book.");
        assertTrue(books.size() <= 10, "Should not scrape more than 10 books.");

        for (Book book : books) {
            assertNotNull(book.getTitle(), "Book title should not be null.");
            assertFalse(book.getTitle().isBlank(), "Book title should not be blank.");
            assertNotNull(book.getPrice(), "Book price should not be null.");
            assertTrue(book.getPrice().matches("£?\\d+(\\.\\d{2})?"), "Book price format seems invalid: " + book.getPrice());
        }
    }

    @Test
    void whenScrapeBookSite_thenParseAndSortBookPrices() {
        BookScraper scraper = new BookScraper();
        Spider.create(scraper)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        List<Book> books = scraper.getBooks();
        assertFalse(books.isEmpty(), "No books were scraped.");

        // Extract numerical prices from string (e.g., £51.77 -> 51.77)
        List<Double> prices = books.stream()
                .map(Book::getPrice)
                .map(p -> p.replace("£", ""))
                .map(Double::parseDouble)
                .toList();

        List<Double> sorted = prices.stream().sorted((a, b) -> Double.compare(b, a)).toList();

        assertEquals(sorted, prices.stream().sorted((a, b) -> Double.compare(b, a)).toList(),
                "Prices are not in descending order after sorting.");
    }

    @Test
    void whenScrapeBookSite_thenBookTitlesShouldContainExpectedWords() {
        BookScraper scraper = new BookScraper();
        Spider.create(scraper)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        List<Book> books = scraper.getBooks();
        assertFalse(books.isEmpty(), "No books were scraped.");

        boolean foundKeyword = books.stream()
                .map(Book::getTitle)
                .anyMatch(title -> title.toLowerCase().matches(".*\\b(book|story|novel|guide|life)\\b.*"));

        assertTrue(foundKeyword, "No book titles contain expected keywords.");
    }


    @Test
    void whenScrapeBookSiteMultipleTimes_thenBookCountShouldStableBetweenRuns() {
        BookScraper scraper1 = new BookScraper();
        Spider.create(scraper1)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        BookScraper scraper2 = new BookScraper();
        Spider.create(scraper2)
                .addUrl("https://books.toscrape.com/")
                .thread(1)
                .run();

        int count1 = scraper1.getBooks().size();
        int count2 = scraper2.getBooks().size();

        assertEquals(count1, count2, "Book count is not stable between two runs.");
    }
}
