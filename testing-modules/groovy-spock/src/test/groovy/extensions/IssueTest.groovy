package extensions

import spock.lang.Issue
import spock.lang.Specification


class IssueTest extends Specification {


    @Issue("http://jira.org/issues/LO-531")
    def 'single issue'() {
    }

    @Issue(["http://jira.org/issues/LO-531", "http://jira.org/issues/LO-123"])
    def 'multiple issues'() {
    }

    @Issue("LO-1000")
    def "I'm using Spock configuration file"() {
        expect:
        true
    }


}
