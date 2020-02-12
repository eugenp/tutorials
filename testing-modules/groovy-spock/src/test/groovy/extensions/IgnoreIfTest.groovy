package extensions

import spock.lang.IgnoreIf
import spock.lang.Specification


class IgnoreIfTest extends Specification {

    @IgnoreIf({System.getProperty("os.name").contains("windows")})
    def "I won't run on windows"() {
        expect:
        true
    }

    @IgnoreIf({ os.isWindows() })
    def "I'm using Spock helper classes to run only on windows"() {
        expect:
        true
    }


}
