package org.baeldung.persistence;

import org.springframework.data.jpa.domain.Specification;

public interface IEnhancedSpecification<T> extends Specification<T> {

	default boolean isOfLowPrecedence() {
		return false;
	}
}
