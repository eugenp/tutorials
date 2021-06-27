package com.baeldung.hexagonal.core.port;

import com.baeldung.hexagonal.core.model.WeightInTime;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightTrackerRepository {
	void addWeight(float weightInKG, LocalDateTime date);
	boolean deleteById(long id);
	List<WeightInTime> getWeightHistory();
}
