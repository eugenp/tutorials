package groovy.com.baeldung.stringtypes

import org.junit.Assert
import org.junit.Test

class TripleSingleQuotedString {

    def 'formatted json'() {
        def jsonContent = '''
        {
            "name": "John",
            "age": 20,
            "birthDate": null
        }
        '''
    }

    def 'triple single quoted'() {
        def triple = '''im triple single quoted string'''
    }

    @Test
    void 'triple single quoted with multiline string'() {
        def triple = '''
            firstline
            secondline
        '''

        Assert.assertTrue(triple.startsWith("\n"))
    }

    @Test
    void 'triple single quoted with multiline string with stripIndent() and removing newline characters'() {
        def triple = '''\
            firstline
            secondline'''.stripIndent()

        Assert.assertEquals("firstline\nsecondline", triple)
    }

    @Test
    void 'triple single quoted with multiline string with last line with only whitespaces'() {
        def triple = '''\
            firstline
                secondline\
        '''.stripIndent()

        println(triple)
    }

    @Test
    void 'triple single quoted with multiline string with stripMargin(Character) and removing newline characters'() {
        def triple = '''\
            |firstline
            |secondline'''.stripMargin()

        println(triple)
    }

    @Test
    void 'striple single quoted with special characters'() {
        def specialCharacters = '''hello \'John\'. This is backslash - \\. \nSecond line starts here'''

        println(specialCharacters)
    }

}
