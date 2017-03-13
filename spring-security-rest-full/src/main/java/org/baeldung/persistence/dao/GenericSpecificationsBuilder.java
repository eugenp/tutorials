package org.baeldung.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.baeldung.persistence.IEnhancedSpecification;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

public class GenericSpecificationsBuilder {

	private final List<SpecSearchCriteria> params;

	public GenericSpecificationsBuilder() {
		this.params = new ArrayList<>();
	}

	public final GenericSpecificationsBuilder with(final String key, final String operation, final Object value,
			final String prefix, final String suffix) {
		return with(null, key, operation, value, prefix, suffix);
	}

	public final GenericSpecificationsBuilder with(final String precedenceIndicator, final String key,
			final String operation, final Object value, final String prefix, final String suffix) {
		SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
		if (op != null) {
			if (op == SearchOperation.EQUALITY) // the operation may be complex
												// operation
			{
				final boolean startWithAsterisk = prefix != null && prefix.contains("*");
				final boolean endWithAsterisk = suffix != null && suffix.contains("*");

				if (startWithAsterisk && endWithAsterisk) {
					op = SearchOperation.CONTAINS;
				} else if (startWithAsterisk) {
					op = SearchOperation.ENDS_WITH;
				} else if (endWithAsterisk) {
					op = SearchOperation.STARTS_WITH;
				}
			}
			params.add(new SpecSearchCriteria(precedenceIndicator, key, op, value));
		}
		return this;
	}

	public <U> Specification<U> build(Function<SpecSearchCriteria, IEnhancedSpecification<U>> converter) {

		if (params.size() == 0) {
			return null;
		}

		final List<IEnhancedSpecification<U>> specs = new ArrayList<>();
		for (final SpecSearchCriteria param : params) {
			specs.add(converter.apply(param));
		}

		specs.sort((spec0, spec1) -> {
			return Boolean.compare(spec0.isOfLowPrecedence(), spec1.isOfLowPrecedence());
		});

		Specification<U> result = specs.get(0);
		for (int i = 1; i < specs.size(); i++) {
			IEnhancedSpecification<U> optionalSpec = specs.get(i);

			if (optionalSpec.isOfLowPrecedence())
				result = Specifications.where(result).or(specs.get(i));
			else
				result = Specifications.where(result).and(specs.get(i));
		}
		return result;
	}
}
