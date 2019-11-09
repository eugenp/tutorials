import org.apache.commons.lang3.RandomStringUtils
import org.junit.Before
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.security.SecureRandom
import java.util.concurrent.ThreadLocalRandom
import kotlin.experimental.and
import kotlin.streams.asSequence
import kotlin.test.assertEquals

const val STRING_LENGTH = 10
const val ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+"

class RandomStringUnitTest {
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    @Test
    fun givenAStringLength_whenUsingJava_thenReturnAlphanumericString() {
        var randomString = ThreadLocalRandom.current()
                .ints(STRING_LENGTH.toLong(), 0, charPool.size)
                .asSequence()
                .map(charPool::get)
                .joinToString("")

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)))
        assertEquals(STRING_LENGTH, randomString.length)
    }

    @Test
    fun givenAStringLength_whenUsingKotlin_thenReturnAlphanumericString() {
        var randomString = (1..STRING_LENGTH).map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)))
        assertEquals(STRING_LENGTH, randomString.length)
    }

    @Test
    fun givenAStringLength_whenUsingApacheCommon_thenReturnAlphanumericString() {
        var randomString = RandomStringUtils.randomAlphanumeric(STRING_LENGTH)

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)))
        assertEquals(STRING_LENGTH, randomString.length)
    }

    @Test
    fun givenAStringLength_whenUsingRandomForBytes_thenReturnAlphanumericString() {
        val random = SecureRandom()
        val bytes = ByteArray(STRING_LENGTH)
        random.nextBytes(bytes)

        var randomString = (0..bytes.size - 1).map { i ->
            charPool.get((bytes[i] and 0xFF.toByte() and (charPool.size-1).toByte()).toInt())
        }.joinToString("")

        assert(randomString.matches(Regex(ALPHANUMERIC_REGEX)))
        assertEquals(STRING_LENGTH, randomString.length)
    }

}