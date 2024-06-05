package com.baeldung.spock.spring


import org.spockframework.spring.StubBeans
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A test to demonstrate the use of the StubBeans annotation")
@StubBeans(DataProvider)
@ContextConfiguration(classes = [AccountService, DataProvider])
class AccountServiceStubBeansTest extends Specification {

    @Autowired
    @Subject
    AccountService accountService

    // 4.1. Test to demonstrate an empty response from a bean stubbed by the @StubBeans annotation
    def "given a Service with a dependency when we use a @StubBeans annotation then a stub is created and injected to the service"() {
        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our StubBeans gave us an empty string response from our DataProvider dependency"
        result == "Fetched: "
    }

}