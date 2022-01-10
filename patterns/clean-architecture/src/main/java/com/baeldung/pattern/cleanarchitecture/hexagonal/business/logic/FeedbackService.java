package com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {
    Optional<Feedback> getFeedback(int id);

    List<Feedback> getAllFeedbacks();

    void createFeedback(String content);
}
