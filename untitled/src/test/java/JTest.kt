import org.junit.Test
import kotlin.test.assertEquals

class JTest {
    @Test
    fun test1(){
        assertEquals(listOf(1,2,3), (1..3).toList())
    }
}