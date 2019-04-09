import extensions.TimeoutTest
import spock.lang.Issue

runner {
    filterStackTrace true

    report {
        issueNamePrefix 'Bug '
        issueUrlPrefix 'http://jira.org/issues/'
    }

    optimizeRunOrder true

//    exclude TimeoutTest
//    exclude {
//        baseClass TimeoutTest
//        annotation Issue
//    }

    report {
        enabled true
        logFileDir '.'
        logFileName 'report.json'
        logFileSuffix new Date().format('yyyy-MM-dd')
    }

}
