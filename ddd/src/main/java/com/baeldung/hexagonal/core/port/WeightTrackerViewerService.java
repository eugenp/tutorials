package com.baeldung.hexagonal.core.port;

import com.baeldung.hexagonal.core.model.WeightInTime;

import java.util.List;

public interface WeightTrackerViewerService {
    List<WeightInTime> getWeightHistory();
}
