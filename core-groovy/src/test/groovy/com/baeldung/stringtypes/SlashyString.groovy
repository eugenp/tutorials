package groovy.com.baeldung.stringtypes

import org.junit.Assert
import org.junit.Test

class SlashyString {

    @Test
    void 'slashy string'() {
        def pattern = /.*foobar.*\/hello.*/

        Assert.assertTrue("I'm matching foobar /hello regexp pattern".matches(pattern))
    }

    void 'wont compile'() {
//        if ('' == //) {
//            println("I can't compile")
//        }
    }

    @Test
    void 'interpolate and multiline'() {
        def name = 'John'

        def example = /
            Hello $name
            second line        
        /
    }

}
