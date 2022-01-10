package com.baeldung.pattern.cleanarchitecture.hexagonal.port.input;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic.FeedbackService;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.dto.FeedbackDto;

import java.util.List;
import java.util.stream.Collectors;

public class ReadingOverFakeRestController implements ReadingOverHttpController {

    private final FeedbackService feedbackService;

    public ReadingOverFakeRestController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks()
          .stream()
          .map(FeedbackDto::new)
          .collect(Collectors.toList());
    }

}
