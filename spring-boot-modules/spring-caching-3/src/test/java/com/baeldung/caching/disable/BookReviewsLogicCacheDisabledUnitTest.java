package com.baeldung.caching.disable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = BookReviewApplication.class)
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource(properties = {
    "appconfig.cache.enabled=false"
})
public class BookReviewsLogicCacheDisabledUnitTest {

    @Autowired
    private BookReviewsLogic bookReviewsLogic;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void givenCacheDisabled_whenLogicExecuted2ndTime_thenItQueriesDB(CapturedOutput output){
        BookReview bookReview = insertBookReview();

        bookReviewsLogic.getBooksByIsbn(bookReview.getIsbn());

        String target = "Hibernate: select br1_0.reviews_id,"
            + "br1_0.book_rating,br1_0.isbn,br1_0.user_id "
            + "from book_reviews br1_0 "
            + "where br1_0.isbn=?";

        String[] logs = output.toString()
            .split("\\r?\\n");
        assertThat(logs).anyMatch(e -> e.contains(target));

        bookReviewsLogic.getBooksByIsbn(bookReview.getIsbn());
        logs = output.toString()
            .split("\\r?\\n");

        long count = Arrays.stream(logs)
            .filter(e -> e.contains(target))
            .count();

        // count 2 means the select query log from 1st and 2nd execution.
        assertEquals(2, count);
    }

    private BookReview insertBookReview() {
        BookReview bookReview = new BookReview();
        bookReview.setReviewsId(123L);
        bookReview.setBookRating("3.2");
        bookReview.setUserId("111");
        bookReview.setIsbn("1234");
        bookRepository.save(bookReview);
        return bookReview;
    }
}
