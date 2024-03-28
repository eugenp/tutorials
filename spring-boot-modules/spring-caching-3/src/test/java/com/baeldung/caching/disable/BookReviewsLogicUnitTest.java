package com.baeldung.caching.disable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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
public class BookReviewsLogicUnitTest {

    @Autowired
    private BookReviewsLogic bookReviewsLogic;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void givenCacheEnabled_whenLogicExecuted2ndTime_thenItDoesntQueriesDB(CapturedOutput output){
        BookReview bookReview = insertBookReview();

        String target = "Hibernate: select bookreview0_.reviews_id as reviews_1_0_, "
            + "bookreview0_.book_rating as book_rat2_0_, "
            + "bookreview0_.isbn as isbn3_0_, "
            + "bookreview0_.user_id as user_id4_0_ "
            + "from book_reviews bookreview0_ "
            + "where bookreview0_.isbn=?";

        // 1st execution
        bookReviewsLogic.getBooksByIsbn(bookReview.getIsbn());
        String[] logs = output.toString()
            .split("\\r?\\n");
        assertThat(logs).anyMatch(e -> e.contains(target));

        // 2nd execution
        bookReviewsLogic.getBooksByIsbn(bookReview.getIsbn());
        logs = output.toString()
            .split("\\r?\\n");
        System.out.println(logs);
        long count = Arrays.stream(logs)
            .filter(e -> e.equals(target))
            .count();

        // count 1 means the select query log from 1st execution.
        assertEquals(1,count);
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
