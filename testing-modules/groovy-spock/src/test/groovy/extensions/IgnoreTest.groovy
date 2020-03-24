package extensions

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class IgnoreTest extends Specification {

    @Ignore
    def "I won't be executed"() {
        expect:
        true
    }

    def 'Example test'() {
        expect:
        true
    }

}
