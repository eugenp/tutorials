package poem.boundary.internal.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import poem.boundary.internal.domain.Poem;
import poem.boundary.internal.domain.RandomPoemPicker;

public class RandomPoemPickerTest {
	private RandomPoemPicker randomPoemPicker;

	@Before
	public void setUp() throws Exception {
		this.randomPoemPicker = new RandomPoemPicker();
	}
	
	@Test
	public void picks_random_poem_from_empty_list() {
		List<Poem> poemsList = createPoemList();
		
		Optional<Poem> randomPoem = randomPoemPicker.pickPoem(poemsList);
		assertFalse(randomPoem.isPresent());
	}
	
	@Test
	public void picks_random_poem_from_single_element_list() {
		List<Poem> poemsList = createPoemList("Poem1");
		
		Optional<Poem> randomPoem = randomPoemPicker.pickPoem(poemsList);
		assertEquals("Poem1", randomPoem.get().toString());
	}

	@Test
	public void picks_random_poem_from_mutiple_element_list() {
		List<Poem> poemsList = createPoemList("Poem1", "Poem2", "Poem3");
		
		Optional<Poem> randomPoem = randomPoemPicker.pickPoem(poemsList);
		assertTrue(poemsList.contains(randomPoem.get()));
	}
	
	private List<Poem> createPoemList(String... poems) {
		List<Poem> poemList = Arrays.stream(poems)
				.map(Poem::new)
				.collect(Collectors.toList());
		return poemList;
	}
}
