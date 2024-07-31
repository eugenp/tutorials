package extensions

import spock.lang.Specification
import spock.util.environment.RestoreSystemProperties


class RestoreSystemPropertiesTest extends Specification {

    @RestoreSystemProperties
    def 'all environment variables will be saved before execution and restored after tests'() {
        given:
        System.setProperty('os.name', 'Mac OS')

        expect:
        true
    }

}
