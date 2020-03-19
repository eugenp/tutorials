package com.baeldung.dddhexagonalspring.domain.service.anagram;

import com.baeldung.dddhexagonalspring.domain.service.anagram.AnagramService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnagramServiceTest {

	private AnagramService service = new AnagramService();

	@Test
	public void testIsAnagramWithNulls() {
		boolean isAnagram = service.isAnagram(null, null);
		Assertions.assertFalse(isAnagram, "Not Anagrams");
	}

	@Test
	public void testIsAnagramWithFirstWordAsNull() {
		boolean isAnagram = service.isAnagram(null, "Hello");
		Assertions.assertFalse(isAnagram, "Not Anagrams");
	}

	@Test
	public void testIsAnagramWithWordWithSpaces() {
		boolean isAnagram = service.isAnagram("    hello     ", " Hello ");
		Assertions.assertTrue(isAnagram, "Anagrams");
	}

	@Test
	public void testIsAnagramWithSecondWordAsNull() {
		boolean isAnagram = service.isAnagram("Hello", null);
		Assertions.assertFalse(isAnagram, "Hello and null word2  are not Anagrams");
	}

	@Test
	public void testIsAnagramWithDifferentLengthWords() {
		boolean isAnagram = service.isAnagram("Hello", "helloWorld");
		Assertions.assertFalse(isAnagram, "Hello and helloWorld are not Anagrams");
	}

	@Test
	public void testIsAnagramUpperCase() {
		boolean isAnagram = service.isAnagram("Hello", "hello");
		Assertions.assertTrue(isAnagram, "'Hello' and 'hello' are  Anagrams");
	}

	@Test
	public void testIsAnagramWithWordAnagram() {
		boolean isAnagram = service.isAnagram("anagram", "margana");
		Assertions.assertTrue(isAnagram, "'anagram' and 'margana' are Anagrams");
	}

	@Test
	public void testIsAnagramEqualLengthWithExtraCharacter() {
		boolean isAnagram = service.isAnagram("anagramm", "marganaa");
		Assertions.assertFalse(isAnagram , "'anagramm' and 'marganaa' are Not Anagrams");
	}

	@Test
	public void testIsAnagramEqualLengthDifferentWords() {
		boolean isAnagram = service.isAnagram("bbcc", "dabc");
		Assertions.assertFalse(isAnagram, "bbcc and dabc are Not Anagrams");
	}

	@Test
	public void testIsAnagramWithAlphaNumericFirstWords() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.isAnagram("anagra)", "margan)");
		});
	}

	@Test
	public void testIsAnagramWithAlphaNumericSecondWords() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.isAnagram("hello", "hel1o");
		});
	}

	@Test
	public void testIsAnagramWithAlphaNumericBothWords() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			service.isAnagram("anagraO", "margan0");
		});
	}

	@Test
	public void testIsAlphaForAlphaWord() {
		boolean isAlpha = service.isAlpha("Hello");
		Assertions.assertTrue(isAlpha, "Is Hello is Alpha ?");
	}

	@Test
	public void testIsAlphaForAlphaNumericWord() {
		boolean isAlpha = service.isAlpha("Hello123");
		Assertions.assertFalse(isAlpha, "Is Hello123 is Alpha ?");
	}

}
