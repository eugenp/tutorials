package com.baeldung.apachecamel;

import java.util.Locale;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

@Tag("live")
class BookInfoLiveTest {

    @Test
    @Timeout(30)
    void givenOpenAIAccess_whenQueryingBookInfo_thenExpectedResponseReceived() throws Exception {
        // Send the prompt to the model
        BookInfo book = BookInfoApp.runChat("Can you provide the author and summary of the following book: The Time Regulation Institute");

        // Validate the basic health of the response
        assertNotNull(book);
        assertNotNull(book.name);
        assertNotNull(book.author);
        assertNotNull(book.summary);
        assertFalse(book.name.isBlank());
        assertFalse(book.author.isBlank());
        assertFalse(book.summary.isBlank());

        // Validate the response
        assertTrue(book.name.toLowerCase()
            .contains("the time regulation institute"));

        String author = book.author.toLowerCase(Locale.ROOT);
        assertTrue(author.contains("tanpınar") || author.contains("tanpinar"));
    }
}
