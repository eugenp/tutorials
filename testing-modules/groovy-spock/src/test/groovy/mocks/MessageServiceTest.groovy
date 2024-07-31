package mocks

import spock.lang.Specification


class MessageServiceTest extends Specification {
    def "should use global mock for UtilityClass"() {
        given:
        def utilityMock = GroovySpy(UtilityClass, global: true)
        utilityMock.getMessage() >> "Mocked Message"

        when:
        MessageService service = new MessageService()
        String message = service.fetchMessage()

        then:
        1 * utilityMock.getMessage()
        message == "Mocked Message"
    }
}