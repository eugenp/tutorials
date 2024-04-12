package com.baeldung.algorithms.kmeans;

import java.util.Map;
import java.util.Objects;

/**
 * Encapsulates all feature values for a few attributes. Optionally each record
 * can be described with the {@link #description} field.
 */
public class Record {

    /**
     * The record description. For example, this can be the artist name for the famous musician
     * example.
     */
    private final String description;

    /**
     * Encapsulates all attributes and their corresponding values, i.e. features.
     */
    private final Map<String, Double> features;

    public Record(String description, Map<String, Double> features) {
        this.description = description;
        this.features = features;
    }

    public Record(Map<String, Double> features) {
        this("", features);
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        String prefix = description == null || description
          .trim()
          .isEmpty() ? "Record" : description;

        return prefix + ": " + features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Record record = (Record) o;
        return Objects.equals(getDescription(), record.getDescription()) && Objects.equals(getFeatures(), record.getFeatures());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getFeatures());
    }
}
