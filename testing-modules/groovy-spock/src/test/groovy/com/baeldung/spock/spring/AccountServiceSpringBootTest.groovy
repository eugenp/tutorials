package com.baeldung.spock.spring

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A test to demonstrate the use of the SpringBean annotation on a SpringBootTest")
@SpringBootTest //(properties = ["spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"])
class AccountServiceSpringBootTest extends Specification {

    @SpringBean
    DataProvider mockProvider = Stub()

    @Autowired
    @Subject
    AccountService accountService

    // 4.4. Overriding an Spring-managed dependency using @SpringBean to inject a Stub using @SpringBootTest
    def "given a Service with a dependency when we use a @SpringBean annotation then our stub is injected to the service"() {
        given: "a stubbed response"
        mockProvider.fetchData(_ as String) >> "42"

        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our SpringBean overrode the original dependency"
        result == "Fetched: 42"
    }

}