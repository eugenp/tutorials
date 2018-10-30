
import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom
import kotlin.test.assertTrue

class RandomNumberTest {

    @Test
    fun whenRandomNumberWithJavaUtilMath_thenResultIsBetween0And1() {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var test = Random().ints(outputStrLength, 0, source.length)
                .asSequence()
                .map(source::get)
                .joinToString("")
        print("message")
    }

}