package com.baeldung.hexagon.internal;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Picks a random poem from a list of poems.
 * 
 * @author b_muth
 *
 */
public class RandomBookPicker {
	private Random random;

	public RandomBookPicker() {
		this.random = new Random();
	}

	/**
	 * Picks a random poem from the specified list of poems.
	 * 
	 * @param poems the poems to pick from
	 * @return a poem from the list, or an empty optional if the input list was empty
	 */
	public Optional<Book> pickBook(List<Book> poems) {
		Objects.requireNonNull(poems);
		
		if (poems.size() == 0) {
			return Optional.empty();
		}

		int randomIndex = random.nextInt(poems.size());
		Book randomPoem = poems.get(randomIndex);
		return Optional.of(randomPoem);
	}
}
