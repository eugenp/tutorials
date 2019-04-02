package extensions

import spock.lang.IgnoreIf
import spock.lang.Specification


class IgnoreIfTest extends Specification {

    @IgnoreIf({System.getProperty("os.name").contains("windows")})
    def "I won't run on windows"() { }

}
