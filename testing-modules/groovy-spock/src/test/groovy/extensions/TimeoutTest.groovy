package extensions

import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

@Timeout(5)
class TimeoutTest extends Specification {

    @Timeout(1)
    def 'I have one second to finish'() {

    }

    def 'I will have 5 seconds timeout'() {}

    @Timeout(value = 200, unit = TimeUnit.SECONDS)
    def 'I will fail after 200 millis'() {
        expect:
        true
    }

}
