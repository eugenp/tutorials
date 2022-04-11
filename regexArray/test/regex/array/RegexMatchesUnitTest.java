package regex.array;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import regex.array.RegexMatches;

class RegexMatchesUnitTest {

	@Test
	void whenFourNums_thenFourMatches() {
		RegexMatches rm = new RegexMatches();
		String actual[] = rm.regexMatch("7801111211fsdafasdfa  7802222222  sadfsadfsda7803333333 sadfdasfasd 7804444444");
		
		assertArrayEquals(new String[] {"7801111211", "7802222222", "7803333333", "7804444444"}, actual, "success");
	}

	@Test
	void whenThreeNums_thenThreeMatches() {
		RegexMatches rm = new RegexMatches();
		String actual[] = rm.regexMatch("7801111211fsdafasdfa  780222222  sadfsadfsda7803333333 sadfdasfasd 7804444444");
		
		assertArrayEquals(new String[] {"7801111211", "7803333333", "7804444444"}, actual, "success");
	}

	@Test
	void whenZeroNums_thenZeroMatches() {
		RegexMatches rm = new RegexMatches();
		String actual[] = rm.regexMatch("78011111fsdafasdfa  780222222  sadfsadfsda78033333 sadfdasfasd 7804444");
		
		assertArrayEquals(new String[] {}, actual, "success");
	}
}
