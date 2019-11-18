package com.baeldung.repository;

import com.baeldung.domain.UserPreference;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, String> {
}
