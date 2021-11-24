package com.baeldung.themes.repository;

import com.baeldung.themes.domain.UserPreference;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPreferenceRepository extends PagingAndSortingRepository<UserPreference, String> {
}
