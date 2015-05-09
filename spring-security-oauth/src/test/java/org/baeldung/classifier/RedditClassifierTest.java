package org.baeldung.classifier;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.baeldung.reddit.classifier.RedditClassifier;
import org.baeldung.reddit.classifier.RedditDataCollector;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class RedditClassifierTest {

    @Test
    public void whenUsingDefaultClassifier_thenAccurate() throws IOException {
        final RedditClassifier classifier = new RedditClassifier();
        classifier.trainClassifier(RedditDataCollector.DATA_FILE);
        final double result = classifier.getAccuracy();
        System.out.println("==== Default Classifier Accuracy = " + result);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++\n\n\n");
        assertTrue(result > 0.7);
    }

    @Test
    public void givenSmallerPoolSizeAndFeatures_whenUsingCustomClassifier_thenAccurate() throws IOException {
        final RedditClassifier classifier = new RedditClassifier(100, 500, 7);
        classifier.trainClassifier(RedditDataCollector.DATA_FILE);
        final double result = classifier.getAccuracy();
        System.out.println("==== Custom Classifier (small) Accuracy = " + result);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++\n\n\n");
        assertTrue(result < 0.7);
    }

    @Test
    public void givenLargerPoolSizeAndFeatures_whenUsingCustomClassifier_thenAccurate() throws IOException {
        final RedditClassifier classifier = new RedditClassifier(250, 2500, 7);
        classifier.trainClassifier(RedditDataCollector.DATA_FILE);
        final double result = classifier.getAccuracy();
        System.out.println("==== Custom Classifier (large) Accuracy = " + result);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++\n\n\n");
        assertTrue(result > 0.7);
    }
}
