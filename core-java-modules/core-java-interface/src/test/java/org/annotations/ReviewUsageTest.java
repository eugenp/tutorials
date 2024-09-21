package org.annotations;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReviewUsageTest {

    @Test
    public void whenMethodAnnotatedWithReviewAnnotationIsCalled_ThenReviewerAndReviewDateIsPopulated()
            throws NoSuchMethodException {
        ReviewUsage reviewUsage = new ReviewUsage();

        Review review = reviewUsage
                .getClass()
                .getDeclaredMethod("service")
                .getAnnotation(Review.class);

        assertEquals("Natasha", review.reviewer());
        assertEquals("2024-08-24", review.date());
    }

}