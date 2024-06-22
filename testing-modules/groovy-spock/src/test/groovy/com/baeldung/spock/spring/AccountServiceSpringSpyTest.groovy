package com.baeldung.spock.spring


import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A selection of tests to demonstrate the use of the SpringSpy annotation for a ContextConfiguration")
@ContextConfiguration(classes = [AccountService, DataProvider])
class AccountServiceSpringSpyTest extends Specification {

    @SpringSpy
    DataProvider dataProvider

    @Autowired
    @Subject
    AccountService accountService

    // 4.3 SpringSpy allowing real method to return result
    def "given a Service with a dependency when we use @SpringSpy just to verify then the original result is returned"() {
        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our SpringSpy was invoked once and allowed the real method to return the result"
        1 * dataProvider.fetchData(_)
        result == "Fetched: data for Something"
    }

    // 4.3 SpringSpy overriding the result
    def "given a Service with a dependency when we use @SpringSpy to override a method then our spy's result is returned"() {
        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our SpringSpy was invoked once and overrode the original method"
        1 * dataProvider.fetchData(_) >> "spied"
        result == "Fetched: spied"
    }

}