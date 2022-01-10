package com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.output.FeedbackRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StandardFeedbackService implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public StandardFeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Optional<Feedback> getFeedback(int id) {
        return Optional.ofNullable(feedbackRepository.findById(id));
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }


    public void createFeedback(String content) {
        Feedback feedback = new Feedback();
        feedback.setContent(content);
        feedback.setCreatedTs(LocalDateTime.now());
        feedbackRepository.insert(feedback);
    }
}
