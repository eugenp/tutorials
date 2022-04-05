package com.baeldung.cactoos;

import java.util.Collection;
import java.util.List;

import org.cactoos.collection.Filtered;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.And;
import org.cactoos.text.FormattedText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CactoosCollectionUtils {

	final Logger LOGGER = LoggerFactory.getLogger(CactoosCollectionUtils.class);

	public void iterateCollection(List<String> strings) throws Exception {
		new And((String input) -> LOGGER.info(new FormattedText("%s\n", input).asString()), strings).value();
	}

	public Collection<String> getFilteredList(List<String> strings) {
		Collection<String> filteredStrings = new ListOf<>(
				new Filtered<>(string -> string.length() == 5, new IterableOf<>(strings)));
		return filteredStrings;
	}

}
