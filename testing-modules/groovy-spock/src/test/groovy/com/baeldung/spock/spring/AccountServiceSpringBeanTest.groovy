package com.baeldung.spock.spring

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A test to demonstrate the use of the SpringBean annotation using a ContextConfiguration")
@ContextConfiguration(classes = [AccountService, DataProvider])
class AccountServiceSpringBeanTest extends Specification {

    @SpringBean
    DataProvider mockProvider = Stub()

    @Autowired
    DataProvider dataProvider

    @Autowired
    @Subject
    AccountService accountService

    // 4.2. Override a Spring-managed dependency using @SpringBean to inject a Stub
    def "given a Service with a dependency when we use a @SpringBean annotation then our stub is injected to the service"() {
        given: "a stubbed response"
        mockProvider.fetchData(_ as String) >> "42"

        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our SpringBean overrode the original dependency"
        result == "Fetched: 42"
    }

}