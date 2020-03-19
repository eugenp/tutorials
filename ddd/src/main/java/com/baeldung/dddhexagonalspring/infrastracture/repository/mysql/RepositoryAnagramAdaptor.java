package com.baeldung.dddhexagonalspring.infrastracture.repository.mysql;

import com.baeldung.dddhexagonalspring.domain.repository.anagram.IAnagramMetricPort;
import org.springframework.stereotype.Component;

@Component
public class RepositoryAnagramAdaptor implements IAnagramMetricPort {
        @Override
        public boolean logMetric(String log) {
                // Save to data storage
                return true;
        }
}
