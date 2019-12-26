package extensions

import spock.lang.IgnoreRest
import spock.lang.Specification


class IgnoreRestTest extends Specification {

    def "I won't run"() { }

    @IgnoreRest
    def 'I will run'() { }

    def "I won't run too"() { }

}
