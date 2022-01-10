package com.baeldung.pattern.cleanarchitecture.hexagonal;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic.FeedbackService;
import com.baeldung.pattern.cleanarchitecture.hexagonal.business.logic.StandardFeedbackService;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.dto.FeedbackDto;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.input.CreationOverFakeMessagingQueue;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.input.CreationOverMessagingQueue;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.input.ReadingOverFakeRestController;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.input.ReadingOverHttpController;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.output.FeedbackRepository;
import com.baeldung.pattern.cleanarchitecture.hexagonal.port.output.InMemoryFeedbackRepository;

import java.util.List;

public class FeedbackApplication {

    public static void main(String[] args) {
        FeedbackRepository feedbackRepository = new InMemoryFeedbackRepository();
        FeedbackService feedbackService = new StandardFeedbackService(feedbackRepository);

        CreationOverMessagingQueue creationOverFakeMessagingQueue;
        creationOverFakeMessagingQueue = new CreationOverFakeMessagingQueue(feedbackService);

        ReadingOverHttpController readingOverHttpController;
        readingOverHttpController = new ReadingOverFakeRestController(feedbackService);

        String firstFeedbackContent = "This application looks great!";
        String secondFeedbackContent = "Need some enhancements :(";
        String thirdFeedbackContent = "Not bad :)";

        creationOverFakeMessagingQueue.listenForFeedback(firstFeedbackContent);
        creationOverFakeMessagingQueue.listenForFeedback(secondFeedbackContent);
        creationOverFakeMessagingQueue.listenForFeedback(thirdFeedbackContent);

        List<FeedbackDto> allFeedbacks = readingOverHttpController.getAllFeedbacks();

        allFeedbacks.forEach(System.out::println);
    }
}
