package org.baeldung.reddit.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.vectorizer.encoders.AdaptiveWordValueEncoder;
import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;

import com.google.common.base.Splitter;

public class RedditClassifier {

    public static int GOOD = 0;
    public static int BAD = 1;
    private final OnlineLogisticRegression classifier;
    private final FeatureVectorEncoder titleEncoder;
    private final FeatureVectorEncoder domainEncoder;

    private final int[] trainCount = { 0, 0 };

    private final int[] evalCount = { 0, 0 };

    public RedditClassifier() {
        classifier = new OnlineLogisticRegression(2, 4, new L2(1));
        titleEncoder = new AdaptiveWordValueEncoder("title");
        titleEncoder.setProbes(1);
        domainEncoder = new StaticWordValueEncoder("domain");
        domainEncoder.setProbes(1);
    }

    public void trainClassifier(String fileName) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(fileName));
        int category;
        Vector features;
        String line = reader.readLine();
        if (line == null) {
            new RedditDataCollector().collectData();
        }

        while ((line != null) && (line != "")) {
            category = (line.startsWith("good")) ? GOOD : BAD;
            trainCount[category]++;
            features = convertLineToVector(line);
            classifier.train(category, features);
            line = reader.readLine();
        }
        reader.close();
        System.out.println("Training count ========= " + trainCount[0] + "___" + trainCount[1]);
    }

    public int classify(Vector features) {
        return classifier.classifyFull(features).maxValueIndex();
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

    public double evaluateClassifier() throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(RedditDataCollector.TEST_FILE));
        int category, result;
        int correct = 0;
        int wrong = 0;
        Vector features;
        String line = reader.readLine();
        while ((line != null) && (line != "")) {
            category = (line.startsWith("good")) ? GOOD : BAD;
            evalCount[category]++;
            features = convertLineToVector(line);
            result = classify(features);
            if (category == result) {
                correct++;
            } else {
                wrong++;
            }
            line = reader.readLine();
        }
        reader.close();
        System.out.println(correct + " ----- " + wrong);
        System.out.println("Eval count ========= " + evalCount[0] + "___" + evalCount[1]);
        return correct / (wrong + correct + 0.0);
    }

    // ==== private
    private Vector convertLineToVector(String line) {
        final Vector features = new RandomAccessSparseVector(4);
        final String[] items = line.split(";");
        titleEncoder.addToVector(items[3], features);
        domainEncoder.addToVector(items[4], features);
        features.set(2, Integer.parseInt(items[1])); // hour of day
        features.set(3, Integer.parseInt(items[2])); // number of words in the title
        return features;
    }

}
