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
    public static int NUM_OF_FEATURES = 1000;

    private final AdaptiveLogisticRegression classifier;
    private final FeatureVectorEncoder titleEncoder;
    private final FeatureVectorEncoder domainEncoder;
    private CrossFoldLearner learner;
    private double accuracy;

    private final int[] trainCount = { 0, 0 };

    private final int[] evalCount = { 0, 0 };

    private final int[] correctCount = { 0, 0 };

    public RedditClassifier() {
        classifier = new AdaptiveLogisticRegression(2, NUM_OF_FEATURES, new L2());
        classifier.setPoolSize(150);
        titleEncoder = new AdaptiveWordValueEncoder("title");
        titleEncoder.setProbes(2);
        domainEncoder = new StaticWordValueEncoder("domain");
        domainEncoder.setProbes(1);
    }

    public void trainClassifier(String fileName) throws IOException {
        final List<NamedVector> vectors = extractVectors(readDataFile(fileName));
        final int noOfTraining = (int) (RedditDataCollector.DATA_SIZE * 0.8);
        final List<NamedVector> trainingData = vectors.subList(0, noOfTraining);
        final List<NamedVector> testData = vectors.subList(noOfTraining, RedditDataCollector.DATA_SIZE);
        int category;
        for (final NamedVector vector : trainingData) {
            category = (vector.getName() == "GOOD") ? GOOD : BAD;
            classifier.train(category, vector);
            trainCount[category]++;
        }
        System.out.println("Training count ========= Good = " + trainCount[0] + " ___ Bad = " + trainCount[1]);
        evaluateClassifier(testData);
    }

    public Vector convertPost(String title, String domain, int hour) {
        final Vector vector = new RandomAccessSparseVector(NUM_OF_FEATURES);
        final List<String> words = Splitter.onPattern("\\W").omitEmptyStrings().splitToList(title);
        vector.set(0, hour);
        vector.set(1, words.size());
        domainEncoder.addToVector(domain, vector);
        for (final String word : words) {
            titleEncoder.addToVector(word, vector);
        }
        return vector;
    }

    public int classify(Vector features) {
        if (learner == null) {
            learner = classifier.getBest().getPayload().getLearner();
        }
        return learner.classifyFull(features).maxValueIndex();
    }

    public double getAccuracy() {
        return accuracy;
    }

    // ==== Private methods

    private void evaluateClassifier(List<NamedVector> vectors) throws IOException {
        int category, result;
        int correct = 0;
        int wrong = 0;
        for (final NamedVector vector : vectors) {
            category = (vector.getName() == "GOOD") ? GOOD : BAD;
            result = classify(vector);

            evalCount[category]++;
            if (category == result) {
                correct++;
                correctCount[result]++;
            } else {
                wrong++;
            }
        }
        System.out.println("Eval count ========= Good = " + evalCount[0] + " ___ Bad = " + evalCount[1]);
        System.out.println("Test result ======== Correct prediction = " + correct + " -----  Wrong prediction = " + wrong);
        System.out.println("Test result ======== Correct Good = " + correctCount[0] + " -----  Correct Bad = " + correctCount[1]);
        System.out.println("Test result ======== Good accuracy = " + (correctCount[0] / (evalCount[0] + 0.0)) + " ----- Bad accuracy = " + (correctCount[1] / (evalCount[1] + 0.0)));
        this.accuracy = correct / (wrong + correct + 0.0);
    }

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
        final NamedVector vector = new NamedVector(new RandomAccessSparseVector(NUM_OF_FEATURES), category);
        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(Long.parseLong(items[1]) * 1000);

        vector.set(0, cal.get(Calendar.HOUR_OF_DAY)); // hour of day
        vector.set(1, Integer.parseInt(items[2])); // number of words in the title
        domainEncoder.addToVector(items[4], vector);
        final String[] words = items[3].split(" ");
        // titleEncoder.setProbes(words.length);
        for (final String word : words) {
            titleEncoder.addToVector(word, vector);
        }
        return vector;
    }

    private String extractCategory(int score) {
        return (score < MIN_SCORE) ? "BAD" : "GOOD";
    }

}
