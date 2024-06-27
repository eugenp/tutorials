package com.baeldung.spock.spring


import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("A selection of tests to demonstrate the use of the SpringBean annotation")
@ContextConfiguration(classes = [AccountService, DataProvider])
class AccountServiceTest extends Specification {

    @Autowired
    DataProvider dataProvider

    @Autowired
    @Subject
    AccountService accountService

    // 3. SpringBean with a real response
    def "given a realdata provider when we use the real bean then we get our usual response"() {
        when: "we fetch our data"
        def result = accountService.getData("Something")

        then: "our real dataProvider responds"
        result == "Fetched: data for Something"
    }

}