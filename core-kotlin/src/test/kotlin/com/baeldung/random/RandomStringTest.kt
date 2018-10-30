import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import kotlin.streams.asSequence
import kotlin.test.assertEquals

const val STRING_LENGTH = 10;
const val ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+";

class RandomStringTest {

    @Test
    fun generateRandomString_useJava_returnString() {
        val charPool = ArrayList<Char>();
        charPool.addAll('a'..'z');
        charPool.addAll('A'..'Z');
        charPool.addAll('0'..'9');

        var randomString = java.util.Random().ints(STRING_LENGTH.toLong(), 0, charPool.size)
                .asSequence()
                .map(charPool::get)
                .joinToString("")

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)));
        assertEquals(STRING_LENGTH, randomString.length);
    }

    @Test
    fun generateRandomString_useKotlin_returnString() {
        val charPool = ArrayList<Char>();
        charPool.addAll('a'..'z');
        charPool.addAll('A'..'Z');
        charPool.addAll('0'..'9');

        var randomString = (1..STRING_LENGTH).map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)));
        assertEquals(STRING_LENGTH, randomString.length);
    }

    @Test
    fun generateRandomString_useApacheCommon_returnString() {
        var randomString = RandomStringUtils.randomAlphanumeric(STRING_LENGTH);

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)));
        assertEquals(STRING_LENGTH, randomString.length);
    }

}