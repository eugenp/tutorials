import com.baeldung.strings.Concatenate
import spock.lang.Specification

class ConcatenateTest extends Specification {

    final Concatenate NAME = new Concatenate(first: 'Joe', last: 'Smith')
    final String EXPECTED = "My name is Joe Smith";

    def "SimpleConcat"() {
        expect:
        NAME.doSimpleConcat() == EXPECTED
    }

    def "ConcatUsingGString"() {
        expect:
        NAME.doConcatUsingGString() == EXPECTED
    }

    def "ConcatUsingGStringClosures"() {
        expect:
        NAME.doConcatUsingGStringClosures() == EXPECTED
    }

    def "ConcatUsingStringConcatMethod"() {
        expect:
        NAME.doConcatUsingStringConcatMethod() == EXPECTED
    }

    def "ConcatUsingLeftShiftOperator"() {
        expect:
        NAME.doConcatUsingLeftShiftOperator() == EXPECTED
    }

    def "ConcatUsingArrayJoinMethod"() {
        expect:
        NAME.doConcatUsingArrayJoinMethod() == EXPECTED
    }

    def "ConcatUsingArrayInjectMethod"() {
        expect:
        NAME.doConcatUsingArrayInjectMethod() == EXPECTED
    }

    def "ConcatUsingStringBuilder"() {
        expect:
        NAME.doConcatUsingStringBuilder() == EXPECTED
    }

    def "ConcatUsingStringBuffer"() {
        expect:
        NAME.doConcatUsingStringBuffer() == EXPECTED
    }

    def "ConcatMultilineUsingStringConcatMethod"() {
        when:
        NAME.first = '''Joe
                        Smith
                        '''
        NAME.last = 'Junior'

        then:
        def expected = '''My name is Joe
                        Smith
                         Junior'''
        NAME.doConcatUsingStringConcatMethod() == expected
    }

    def "GStringvsClosure"() {
        given:
        def eagerGString = "My name is $NAME.first $NAME.last"
        def lazyGString = "My name is ${-> NAME.first} ${-> NAME.last}"

        expect:
        eagerGString == "My name is Joe Smith"
        lazyGString == "My name is Joe Smith"
    }

    def "LazyVsEager"() {
        given:
        def eagerGString = "My name is $NAME.first $NAME.last"
        def lazyGString = "My name is ${-> NAME.first} ${-> NAME.last}"
        NAME.first = "David"

        expect:
        eagerGString == "My name is Joe Smith"
        lazyGString == "My name is David Smith"
    }
}
