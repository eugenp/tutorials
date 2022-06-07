package groovy.com.baeldung.stringtypes

import org.junit.Assert
import org.junit.Test

class Strings {

    @Test
    void 'string interpolation '() {
        def name = "Kacper"

        def result = "Hello ${name}!"

        Assert.assertEquals("Hello Kacper!", result.toString())
    }

    @Test
    void 'string concatenation'() {
        def first = "first"
        def second = "second"

        def concatenation = first + second

        Assert.assertEquals("firstsecond", concatenation)
    }
}
