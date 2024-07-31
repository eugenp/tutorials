package extensions

import spock.lang.See
import spock.lang.Specification


class SeeTest extends Specification {


    @See("https://example.org")
    def 'Look at the reference'() {

    }

    @See(["https://example.org/first", "https://example.org/first"])
    def 'Look at the references'() {

    }

}
