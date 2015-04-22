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

    private final int[] trainCount = { 0, 0 };
    private final int[] evalCount = { 0, 0 };
    private final int[] correctCount = { 0, 0 };

    private final AdaptiveLogisticRegression classifier;
    private final FeatureVectorEncoder titleEncoder;
    private final FeatureVectorEncoder domainEncoder;
    private final int noOfFeatures;
    private final int minScore;
    private CrossFoldLearner learner;
    private double accuracy;

    public RedditClassifier() {
        this(150, 1000, 7);
    }

    public RedditClassifier(final int poolSize, final int noOfFeatures, int minScore) {
        this.minScore = minScore;
        this.noOfFeatures = noOfFeatures;
        classifier = new AdaptiveLogisticRegression(2, noOfFeatures, new L2());
        classifier.setPoolSize(poolSize);
        titleEncoder = new AdaptiveWordValueEncoder("title");
        titleEncoder.setProbes(1);
        domainEncoder = new StaticWordValueEncoder("domain");
        domainEncoder.setProbes(1);
    }

    // API

    public void trainClassifier(final String fileName) throws IOException {
        final List<NamedVector> vectors = extractVectors(readDataFile(fileName));
        final int size = vectors.size();
        final int noOfTraining = (int) (size * 0.8);
        final List<NamedVector> trainingData = vectors.subList(0, noOfTraining);
        final List<NamedVector> testData = vectors.subList(noOfTraining, size);
        int category;
        for (final NamedVector vector : trainingData) {
            category = (vector.getName() == "GOOD") ? GOOD : BAD;
            classifier.train(category, vector);
            trainCount[category]++;
        }
        System.out.println("Training count ========= Good = " + trainCount[0] + " ___ Bad = " + trainCount[1]);
        System.out.println("----------------------------------------------------------------- \n");
        evaluateClassifier(testData);
    }

    public Vector convertPost(final String title, final String domain, final int hour) {
        final Vector vector = new RandomAccessSparseVector(noOfFeatures);
        final List<String> words = Splitter.onPattern("\\W").omitEmptyStrings().splitToList(title);
        vector.set(0, hour);
        vector.set(1, words.size());
        domainEncoder.addToVector(domain, vector);
        for (final String word : words) {
            titleEncoder.addToVector(word, vector);
        }
        return vector;
    }

    public int classify(final Vector features) {
        if (learner == null) {
            learner = classifier.getBest().getPayload().getLearner();
        }
        return learner.classifyFull(features).maxValueIndex();
    }

    public double getAccuracy() {
        return accuracy;
    }

    // ==== Private methods

    private void evaluateClassifier(final List<NamedVector> vectors) throws IOException {
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
        System.out.println("Eval count   =================== Good = " + evalCount[0] + " ----- Bad = " + evalCount[1] + "\n");
        System.out.println("Overall Evaluation ============= Correct prediction = " + correct + " -----  Wrong prediction = " + wrong);
        System.out.println("Correctly Evaluated  =========== Correct Good = " + correctCount[0] + " -----  Correct Bad = " + correctCount[1]);
        System.out.println("Correctly Evaluated (%) ======== Good accuracy = " + (correctCount[0] / (evalCount[0] + 0.0)) + " ----- Bad accuracy = " + (correctCount[1] / (evalCount[1] + 0.0)));
        this.accuracy = correct / (wrong + correct + 0.0);
    }

    private List<String> readDataFile(final String fileName) throws IOException {
        List<String> lines = Files.readLines(new File(fileName), Charset.forName("utf-8"));
        if ((lines == null) || (lines.size() == 0)) {
            new RedditDataCollector().collectData();
            lines = Files.readLines(new File(fileName), Charset.forName("utf-8"));
        }
        lines.remove(0);
        return lines;
    }

    private List<NamedVector> extractVectors(final List<String> lines) {
        final List<NamedVector> vectors = new ArrayList<NamedVector>(lines.size());
        for (final String line : lines) {
            vectors.add(extractVector(line));
        }
        return vectors;
    }

    private NamedVector extractVector(final String line) {
        final String[] items = line.split(",");
        final String numberOfVotes = items[0];
        final String time = items[1];
        final String numberOfWordInTitle = items[2];
        final String title = items[3];
        final String theRootDomain = items[4];

        final RandomAccessSparseVector internalVector = new RandomAccessSparseVector(noOfFeatures);

        final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(Long.parseLong(time) * 1000);
        internalVector.set(0, cal.get(Calendar.HOUR_OF_DAY)); // hour of day

        internalVector.set(1, Integer.parseInt(numberOfWordInTitle)); // number of words in the title

        domainEncoder.addToVector(theRootDomain, internalVector);
        final List<String> words = Splitter.on(' ').splitToList(title);
        words.stream().filter(word -> word.length() > 2).forEach(word -> titleEncoder.addToVector(word, internalVector));

        final String category = extractCategory(Integer.parseInt(numberOfVotes));
        return new NamedVector(internalVector, category);
    }

    private String extractCategory(final int score) {
        return (score < minScore) ? "BAD" : "GOOD";
    }

}
