package com.baeldung.pattern.cleanarchitecture.hexagonal.port.input;

import com.baeldung.pattern.cleanarchitecture.hexagonal.port.dto.FeedbackDto;

import java.util.List;

public interface ReadingOverHttpController {
    List<FeedbackDto> getAllFeedbacks();
}
