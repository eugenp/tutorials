package groovy.com.baeldung.stringtypes

import org.junit.Test

class TripleDoubleQuotedString {

    @Test
    void 'triple-quoted strings with interpolation'() {
        def name = "John"

        def multiLine = """
            I'm $name.
            "This is quotation"
        """

        println(multiLine)
    }

}
