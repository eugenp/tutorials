package com.baeldung.hexagonal.core.port;

import java.time.LocalDateTime;

public interface WeightTrackerEditorService {
	void addWeight(float weightInKG, LocalDateTime date);
	boolean remove(long id);
}
