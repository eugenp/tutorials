package extensions

import spock.lang.Requires
import spock.lang.Specification


class RequiresTest extends Specification {

    @Requires({ System.getProperty("os.name").contains("windows") })
    def "I will run only on Windows"() {
        expect:
        true
    }
}
