package org.baeldung.classifier;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.baeldung.reddit.classifier.RedditClassifier;
import org.baeldung.reddit.classifier.RedditDataCollector;
import org.junit.Before;
import org.junit.Test;

public class RedditClassifierTest {

    private RedditClassifier classifier;

    @Before
    public void init() throws IOException {
        classifier = new RedditClassifier();
        classifier.trainClassifier(RedditDataCollector.TRAINING_FILE);
    }

    @Test
    public void testClassifier() throws IOException {
        final double result = classifier.evaluateClassifier();
        System.out.println("Accuracy = " + result);
        assertTrue(result > 0.8);
    }
}
