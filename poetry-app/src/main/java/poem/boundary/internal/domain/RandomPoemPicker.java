package poem.boundary.internal.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * Picks a random poem from a list of poems.
 * 
 *
 */
public class RandomPoemPicker {
	private Random random;

	public RandomPoemPicker() {
		this.random = new Random();
	}

	/**
	 * Picks a random poem from the specified list of poems.
	 * 
	 * @param poems the poems to pick from
	 * @return a poem from the list, or an empty optional if the input list was empty
	 */
	public Optional<Poem> pickPoem(List<Poem> poems) {
		Objects.requireNonNull(poems);
		
		if (poems.size() == 0) {
			return Optional.empty();
		}

		int randomIndex = random.nextInt(poems.size());
		Poem randomPoem = poems.get(randomIndex);
		return Optional.of(randomPoem);
	}
}
