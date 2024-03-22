package com.baeldung.spock.capture


import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A selection of tests to demonstrate capturing arguments")
class ArgumentCaptureUnitTest extends Specification {

    // 3. Preparing Our Data-Driven Test
    def "given a Stub when we invoke it then we capture the stubbed response"() {
        given: "an input and a result"
        def input = "Input"
        def stubbedResponse = "42"

        and: "a Stub for our response"
        @Subject
        ArgumentCaptureSubject stubClass = Stub()
        stubClass.catchMeIfYouCan(_) >> stubbedResponse

        when: "we invoke our Stub's method"
        def result = stubClass.catchMeIfYouCan(input)

        then: "we get our stubbed response"
        result == stubbedResponse
    }


    // 4. Capturing Arguments - argument list and a stubbed response
    def "given a Stub when we invoke it then we capture the list of arguments and return a stubbed result"() {
        given: "an input and a result"
        def input = "Input"
        def stubbedResponse = "42"

        and: "a variable and a Stub with a Closure to capture our arguments"
        def captured
        @Subject
        ArgumentCaptureSubject stubClass = Stub()
        stubClass.catchMeIfYouCan(_) >> { arguments -> captured = arguments; stubbedResponse }

        when: "we invoke our method"
        def result = stubClass.catchMeIfYouCan(input)

        then: "what we captured matches the input"
        captured[0] == input
        result == stubbedResponse
    }


    // 4. Capturing Arguments - single argument
    def "given a Stub when we invoke it then we capture the argument"() {
        given: "an input and a result"
        def input = "Input"

        and: "a variable and a Stub with a Closure to capture our arguments"
        @Subject
        def captured
        ArgumentCaptureSubject stubClass = Stub()
        stubClass.catchMeIfYouCan(_) >> { arguments -> captured = arguments[0] }

        when: "we invoke our method"
        stubClass.catchMeIfYouCan(input)

        then: "what we captured matches the input"
        captured == input
    }


    // 4. Capturing Arguments - single argument and a stubbed response
    def "given a Stub when we invoke it then we capture the argument and return a stubbed result"() {
        given: "an input and a result"
        def input = "Input"
        def stubbedResponse = "42"

        and: "a variable and a Stub with a Closure to capture our variable and return a stubbed response"
        @Subject
        def captured
        ArgumentCaptureSubject stubClass = Stub()
        stubClass.catchMeIfYouCan(_) >> { arguments -> captured = arguments[0]; stubbedResponse }

        when: "we invoke our method"
        def result = stubClass.catchMeIfYouCan(input)

        then: "what we captured matches the input and we got our stubbed response"
        captured == input
        result == stubbedResponse
    }


    // 5. Capturing with Spies - calling real method
    def "given a Spy when we invoke it then we capture the argument and then delegate to the real method"() {
        given: "an input string"
        def input = "Input"

        and: "a variable and a Spy with a Closure to capture the argument and call the underlying method"
        def captured
        @Subject
        ArgumentCaptureSubject spyClass = Spy()
        spyClass.catchMeIfYouCan(_) >> { arguments -> captured = arguments[0]; callRealMethod() }

        when: "we invoke our method"
        def result = spyClass.catchMeIfYouCan(input)

        then: "what we captured matches the input and our result comes from the real method"
        captured == input
        result == "Received Input"
    }

    // 5. Capturing with Spies - tampering with the arguments
    def "given a Spy when we invoke it then we capture the argument and then delegate to the real method with a different value"() {
        given: "an input string"
        def input = "Input"

        and: "a variable and a Spy with a Closure to capture and change the first argument then call the underlying method"
        def captured
        @Subject
        ArgumentCaptureSubject spyClass = Spy()
        spyClass.catchMeIfYouCan(_) >> { arguments -> captured = arguments[0]; callRealMethodWithArgs('Tampered:' + captured) }

        when: "we invoke our method"
        def result = spyClass.catchMeIfYouCan(input)

        then: "what we captured matches the input"
        captured == input
        result == "Received Tampered:Input"
    }


    // 6. Capturing Arguments Using an Injected Mock
    def "given an internal method call when we invoke our subject then we capture the internal argument and return the result of the real method"() {
        given: "a variable and a Spy with a Closure to capture the first argument and call the underlying method"
        def captured
        ArgumentCaptureDependency spyClass = Spy()
        spyClass.catchMe(_) >> { arguments -> captured = arguments[0]; callRealMethod() }

        and: "our subject with an injected Spy"
        @Subject
        def argumentCaptureSubject = new ArgumentCaptureSubject(spyClass)

        when: "we invoke our method"
        def result = argumentCaptureSubject.callOtherClass()

        then: "what we captured matches the input"
        captured == "Internal Parameter"
        result == "***Internal Parameter***"
    }


    // 7. Capturing Multiple Invocations
    def "given an dynamic Mock when we invoke our subject then we capture the argument for each invocation"() {
        given: "a variable for our captured arguments and a mock to capture them"
        def capturedStrings = new ArrayList<String>()
        ArgumentCaptureDependency mockClass = Mock()

        and: "our subject"
        @Subject
        def argumentCaptureSubject = new ArgumentCaptureSubject(mockClass)

        when: "we invoke our method"
        argumentCaptureSubject.callOtherClass("First")
        argumentCaptureSubject.callOtherClass("Second")

        then: "our method was called twice and captured the argument"
        2 * mockClass.catchMe(_ as String) >> { arguments ->
            capturedStrings.add(arguments[0])
        }

        and: "our captured list contains an entry for both of our input values"
        capturedStrings[0] == "First"
        capturedStrings[1] == "Second"
        capturedStrings.contains("First")
        capturedStrings.contains("Second")
    }


    // 8. Using Multiple then Blocks
    def "given a Mock when we invoke our subject twice then our Mock verifies the sequence"() {
        given: "a mock"
        ArgumentCaptureDependency mockClass = Mock()

        and: "our subject"
        @Subject argumentCaptureSubject = new ArgumentCaptureSubject(mockClass)

        when: "we invoke our method"
        argumentCaptureSubject.callOtherClass("First")
        argumentCaptureSubject.callOtherClass("Second")

        then: "we invoked our Mock with 'First' the first time"
        1 * mockClass.catchMe( "First")

        then: "we invoked our Mock with 'Second' the next time"
        1 * mockClass.catchMe( "Second")
    }


    // 8. Using Multiple then Blocks - demonstrate error message
    @PendingFeature(reason = "Remove this annotation to fail this test and see what message Spock issues when invocations occur in the wrong sequence")
    def "given a Mock when we invoke our subject in the wrong order then Spock's Mock detects the wrong sequence"() {
        given: "an input string"
        def input = "Input"

        and: "a mock and a variable for our captured argument"
        ArgumentCaptureDependency mockClass = Mock()

        and: "our subject"
        @Subject argumentCaptureSubject = new ArgumentCaptureSubject(mockClass)

        when: "we invoke our method"
        argumentCaptureSubject.callOtherClass("Second")
        argumentCaptureSubject.callOtherClass("First")

        then: "we invoked our Mock with 'First' the first time"
        1 * mockClass.catchMe( "First")

        then: "we invoked our Mock with 'Second' the next time"
        1 * mockClass.catchMe( "Second")
    }

}
