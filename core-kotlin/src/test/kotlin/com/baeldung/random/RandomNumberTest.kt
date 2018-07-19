
import org.junit.jupiter.api.Test
import java.util.concurrent.ThreadLocalRandom

class RandomNumberTest {

    @Test
    fun givenUsingJavaUtilMath_whenGeneratingRandomNumber_thenCorrect() {
        val randomNumber = Math.random()
        println(randomNumber)
    }

    @Test
    fun givenUsingThreadLocalRandom_whenGeneratingRandomNumber_thenCorrect() {
        val randomDouble = ThreadLocalRandom.current().nextDouble()
        val randomInteger = ThreadLocalRandom.current().nextInt()
        val randomLong = ThreadLocalRandom.current().nextLong()
        println(randomDouble)
        println(randomInteger)
        println(randomLong)
    }

    @Test
    fun givenUsingKotlinJsMath_whenGeneratingRandomNumber_thenCorrect() {
        val randomDouble = Math.random()
        println(randomDouble)
    }

    @Test
    fun givenUsingKotlinNumberRange_whenGeneratingRandomNumberInGivenRange_thenCorrect() {
        val randomInteger = (1..12).shuffled().first()
        println(randomInteger)
    }

    @Test
    fun givenUsingThreadLocalRandom_whenGeneratingRandomNumberInGivenRange_thenCorrect() {
        val randomDouble = ThreadLocalRandom.current().nextDouble(1.0, 10.0)
        val randomInteger = ThreadLocalRandom.current().nextInt(1, 10)
        val randomLong = ThreadLocalRandom.current().nextLong(1, 10)
        println(randomDouble)
        println(randomInteger)
        println(randomLong)
    }

}