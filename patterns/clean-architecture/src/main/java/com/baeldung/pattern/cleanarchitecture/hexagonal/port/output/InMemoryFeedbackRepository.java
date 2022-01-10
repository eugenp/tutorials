package com.baeldung.pattern.cleanarchitecture.hexagonal.port.output;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryFeedbackRepository implements FeedbackRepository {

    private static final Map<Integer, Feedback> FEEDBACK_DB = new ConcurrentHashMap<>();
    private static final AtomicInteger PRIMARY_ID_SEQUENCE = new AtomicInteger(0);

    @Override
    public Feedback findById(int id) {
        return FEEDBACK_DB.get(id);
    }

    @Override
    public List<Feedback> findAll() {
        return new ArrayList<>(FEEDBACK_DB.values());
    }

    @Override
    public int insert(Feedback feedback) {
        feedback.setId(PRIMARY_ID_SEQUENCE.incrementAndGet());
        FEEDBACK_DB.put(feedback.getId(), feedback);
        return feedback.getId();
    }

}
