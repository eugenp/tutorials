package extensions

import spock.lang.Specification
import spock.lang.Stepwise


@Stepwise
class StepwiseTest extends Specification {

    def 'I will run as first'() { }

    def 'I will run as second'() { }

}
