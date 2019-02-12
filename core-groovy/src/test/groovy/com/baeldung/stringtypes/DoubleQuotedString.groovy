package groovy.com.baeldung.stringtypes

import org.junit.Assert
import org.junit.Test

class DoubleQuotedString {

    @Test
    void 'escape double quoted string'() {
        def example = "Hello \"world\"!"

        println(example)
    }

    @Test
    void 'String ang GString'() {
        def string = "example"
        def stringWithExpression = "example${2}"

        Assert.assertTrue(string instanceof String)
        Assert.assertTrue(stringWithExpression instanceof GString)
        Assert.assertTrue(stringWithExpression.toString() instanceof String)
    }

    @Test
    void 'placeholder with variable'() {
        def name = "John"
        def helloName = "Hello $name!".toString()

        Assert.assertEquals("Hello John!", helloName)
    }

    @Test
    void 'placeholder with expression'() {
        def result = "result is ${2 * 2}".toString()

        Assert.assertEquals("result is 4", result)
    }

    @Test
    void 'placeholder with dotted access'() {
        def person = [name: 'John']

        def myNameIs = "I'm $person.name, and you?".toString()

        Assert.assertEquals("I'm John, and you?", myNameIs)
    }

    @Test
    void 'placeholder with method call'() {
        def name = 'John'

        def result = "Uppercase name: ${name.toUpperCase()}".toString()

        Assert.assertEquals("Uppercase name: JOHN", result)
    }


    @Test
    void 'GString and String hashcode'() {
        def string = "2+2 is 4"
        def gstring = "2+2 is ${4}"

        Assert.assertTrue(string.hashCode() != gstring.hashCode())
    }

}
