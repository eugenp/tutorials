package extensions

import spock.lang.Retry
import spock.lang.Specification

@Retry
class RetryTest extends Specification {

    @Retry
    def 'I will retry three times'() { }

    @Retry(exceptions = [RuntimeException])
    def 'I will retry only on RuntimeException'() { }

    @Retry(condition = { failure.message.contains('error') })
    def 'I will retry with a specific message'() { }

    @Retry(delay = 1000)
    def 'I will retry after 1000 millis'() { }
}
