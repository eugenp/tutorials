package com.baeldung.pattern.cleanarchitecture.hexagonal.port.output;

import com.baeldung.pattern.cleanarchitecture.hexagonal.business.model.Feedback;
import java.util.List;

public interface FeedbackRepository {

  Feedback findById(int id);

  List<Feedback> findAll();

  int insert(Feedback feedback);

}
