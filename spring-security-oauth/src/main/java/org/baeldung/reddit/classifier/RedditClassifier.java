package org.baeldung.reddit.classifier;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression;
import org.apache.mahout.classifier.sgd.CrossFoldLearner;
import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.AdaptiveWordValueEncoder;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;

import com.google.common.base.Splitter;
import com.google.common.io.Files;

public class RedditClassifier {

    public static int GOOD = 0;
    public static int BAD = 1;
    public static int MIN_SCORE = 10;
    private final AdaptiveLogisticRegression classifier;
    private final FeatureVectorEncoder titleEncoder;
    private final FeatureVectorEncoder domainEncoder;
    private CrossFoldLearner learner;

    private final int[] trainCount = { 0, 0 };

    private final int[] evalCount = { 0, 0 };

    public RedditClassifier() {
        classifier = new AdaptiveLogisticRegression(2, 4, new L2());
        classifier.setPoolSize(25);
        titleEncoder = new AdaptiveWordValueEncoder("title");
        titleEncoder.setProbes(1);
        domainEncoder = new StaticWordValueEncoder("domain");
        domainEncoder.setProbes(1);
    }

    public void trainClassifier(String fileName) throws IOException {
        final List<NamedVector> vectors = extractVectors(readDataFile(fileName));
        int category;
        for (final NamedVector vector : vectors) {
            category = (vector.getName() == "GOOD") ? GOOD : BAD;
            classifier.train(category, vector);
            trainCount[category]++;
        }
        System.out.println("Training count ========= " + trainCount[0] + "___" + trainCount[1]);
    }

    public double evaluateClassifier() throws IOException {
        final List<NamedVector> vectors = extractVectors(readDataFile(RedditDataCollector.TEST_FILE));
        int category, result;
        int correct = 0;
        int wrong = 0;
        for (final NamedVector vector : vectors) {
            category = (vector.getName() == "GOOD") ? GOOD : BAD;
            result = classify(vector);

            evalCount[category]++;
            if (category == result) {
                correct++;
            } else {
                wrong++;
            }
        }
        System.out.println(correct + " ----- " + wrong);
        System.out.println("Eval count ========= " + evalCount[0] + "___" + evalCount[1]);
        return correct / (wrong + correct + 0.0);
    }

    public Vector convertPost(String title, String domain, int hour) {
        final Vector features = new RandomAccessSparseVector(4);
        final int noOfWords = Splitter.onPattern("\\W").omitEmptyStrings().splitToList(title).size();
        titleEncoder.addToVector(title, features);
        domainEncoder.addToVector(domain, features);
        features.set(2, hour);
        features.set(3, noOfWords);
        return features;
    }

    public int classify(Vector features) {
        if (learner == null) {
            learner = classifier.getBest().getPayload().getLearner();
        }
        return learner.classifyFull(features).maxValueIndex();
    }

    // ==== Private methods

    private List<String> readDataFile(String fileName) throws IOException {
        List<String> lines = Files.readLines(new File(fileName), Charset.forName("utf-8"));
        if ((lines == null) || (lines.size() == 0)) {
            new RedditDataCollector().collectData();
            lines = Files.readLines(new File(fileName), Charset.forName("utf-8"));
        }
        lines.remove(0);
        return lines;
    }

    private List<NamedVector> extractVectors(List<String> lines) {
        final List<NamedVector> vectors = new ArrayList<NamedVector>(lines.size());
        for (final String line : lines) {
            vectors.add(extractVector(line));
        }
        return vectors;
    }

    private NamedVector extractVector(String line) {
        final String[] items = line.split(",");
        final String category = extractCategory(Integer.parseInt(items[0]));
        final NamedVector vector = new NamedVector(new RandomAccessSparseVector(4), category);
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(Long.parseLong(items[1]) * 1000);

        titleEncoder.addToVector(items[3], vector);
        domainEncoder.addToVector(items[4], vector);
        vector.set(2, cal.get(Calendar.HOUR_OF_DAY)); // hour of day
        vector.set(3, Integer.parseInt(items[2])); // number of words in the title

        return vector;
    }

    private String extractCategory(int score) {
        return (score < MIN_SCORE) ? "BAD" : "GOOD";
    }

}
