package com.baeldung.springai.evaluator;

public record QualityAssuranceEvaluationResponse(
    boolean pass,
    float score,
    String feedback) {
}