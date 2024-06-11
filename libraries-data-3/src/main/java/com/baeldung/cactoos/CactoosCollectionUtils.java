package com.baeldung.cactoos;

import java.util.Collection;
import java.util.List;

import org.cactoos.func.FuncOf;
import org.cactoos.iterable.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.iterable.Mapped;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.And;
import org.cactoos.scalar.True;
import org.cactoos.text.FormattedText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CactoosCollectionUtils {

	final Logger LOGGER = LoggerFactory.getLogger(CactoosCollectionUtils.class);

	public void iterateCollection(List<String> strings) throws Exception {
		new And(
			new Mapped<>(
				new FuncOf<>(
					input -> System.out.printf("Item: %s\n", input),
					new True()
				),
				strings
			)
		).value();
	}

	public Collection<String> getFilteredList(List<String> strings) {
        return new ListOf<>(
			new Filtered<>(
				s -> s.length() == 5,
				strings
			)
		);
	}

}
