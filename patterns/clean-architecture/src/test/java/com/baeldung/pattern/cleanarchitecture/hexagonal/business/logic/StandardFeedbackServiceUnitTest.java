package com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.output.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StandardFeedbackServiceUnitTest {

    @Mock
    FeedbackRepository feedbackRepository;
    @InjectMocks
    StandardFeedbackService standardFeedbackService;

    @Captor
    ArgumentCaptor<Feedback> feedbackArgumentCaptor;

    @Test
    void givenSingleContent_whenCreation_thenRepositoryShouldBeCalled() {
        String content = "Awesome app!";

        standardFeedbackService.createFeedback(content);

        verify(feedbackRepository, times(1))
          .insert(feedbackArgumentCaptor.capture());

        Feedback actualFeedback = feedbackArgumentCaptor.getValue();

        assertThat(actualFeedback).isNotNull();
        assertThat(actualFeedback.getContent()).isEqualTo(content);
        assertThat(actualFeedback.getCreatedTs()).isNotNull();
    }

    @Test
    void givenFeedback_whenGetCalled_thenShouldReturnFeedback() {
        Feedback feedback = new Feedback();
        feedback.setId(12);
        feedback.setCreatedTs(LocalDateTime.now());
        feedback.setContent("Crashing ... :(");

        when(feedbackRepository.findById(12)).thenReturn(feedback);

        Optional<Feedback> actualMaybeFeedback = standardFeedbackService.getFeedback(12);

        assertThat(actualMaybeFeedback.isPresent()).isTrue();
        Feedback actualFeedback = actualMaybeFeedback.get();
        assertThat(actualFeedback.getContent()).isEqualTo("Crashing ... :(");
        assertThat(actualFeedback.getCreatedTs()).isNotNull();
    }

    @Test
    void givenNoFeedback_whenGetCalled_thenShouldReturnEmpty() {
        when(feedbackRepository.findById(12)).thenReturn(null);

        Optional<Feedback> actualMaybeFeedback = standardFeedbackService.getFeedback(12);

        assertThat(actualMaybeFeedback.isPresent()).isFalse();
    }

    @Test
    void givenMultipleFeedbacks_whenGetAll_thenShouldReturnAll() {
        Feedback firstFeedback = new Feedback();
        firstFeedback.setId(12);
        firstFeedback.setCreatedTs(LocalDateTime.now());
        firstFeedback.setContent("Crashing ... :(");

        Feedback secondFeedback = new Feedback();
        secondFeedback.setId(16);
        secondFeedback.setCreatedTs(LocalDateTime.now());
        secondFeedback.setContent("So fast, like it!");

        List<Feedback> feedbacks = Arrays.asList(firstFeedback, secondFeedback);

        when(feedbackRepository.findAll()).thenReturn(feedbacks);

        List<Feedback> allActualFeedbacks = standardFeedbackService.getAllFeedbacks();

        assertThat(allActualFeedbacks).hasSize(2);

        assertThat(allActualFeedbacks.get(0).getContent()).isEqualTo("Crashing ... :(");
        assertThat(allActualFeedbacks.get(0).getCreatedTs()).isNotNull();

        assertThat(allActualFeedbacks.get(1).getContent()).isEqualTo("So fast, like it!");
        assertThat(allActualFeedbacks.get(1).getCreatedTs()).isNotNull();
    }

}