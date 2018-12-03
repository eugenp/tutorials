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

class ConstantUnitTest {

    @Test
    fun givenConstant_whenCompare_thenReturnTrue() {
        assertEquals(20, SingletonTestObject.COMPILE_TIME_CONST);
        assertEquals(20, SingletonTestObject.RUN_TIME_CONST);
        assertEquals(20, SingletonTestObject.JAVA_STATIC_FINAL_FIELD);


        assertEquals(20, TestClass.COMPANION_OBJECT_NUMBER);
    }
}

object SingletonTestObject {
    const val COMPILE_TIME_CONST = 20

    val RUN_TIME_CONST: Int

    @JvmStatic
    val JAVA_STATIC_FINAL_FIELD = 20

    init {
        RUN_TIME_CONST = 20;
    }
}

class TestClass {
    companion object {
        const val COMPANION_OBJECT_NUMBER = 20
    }
}

