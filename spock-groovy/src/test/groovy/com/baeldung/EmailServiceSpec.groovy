import spock.lang.Specification

class EmailServiceSpec extends Specification {

    def "Should send the email and log the invoice when the total amount of the invoice is greater than zero"() {

        given: "a mock of LogService"
        def logService = Mock(LogService)

        and: "an instance of EmailService"
        def emailService = new EmailService(logService)

        when: "an attempt to send an email is made"
        def actualEmailSent = emailService.sendEmail(invoice)

        then: "the email is sent only if the total amount of the invoice is greater than zero"
        assert actualEmailSent == expectedEmailSent

        and: "a log statement is produced only if the email is sent successfully"
        expectedLogCalls * logService.log(invoice, 'Email sent')

        where:
        invoice                                   || expectedEmailSent || expectedLogCalls
        [totalAmount: BigDecimal.ZERO] as Invoice || false             || 0
        [totalAmount: BigDecimal.ONE] as Invoice  || true              || 1
    }

    def "Should return true if the email is sent successfully to the client"() {

        given: "an log"
        def invoice = new Invoice(totalAmount: BigDecimal.TEN)

        and: "a fake LogService"
        def logService = Stub(LogService)

        logService.log(_, _) >> {}

        and: "an instance of EmailService"
        def emailService = new EmailService(logService)

        when: "an attempt to send an email with an log is made"
        def emailSent = emailService.sendEmail(invoice)

        then: "the email is sent successfully"
        emailSent
    }

    def "Should invoke the correct overloaded log method when an email is sent successfully sent to the client"() {

        given: "spy based on LogService"
        def spyLogService = Spy(LogService)

        and: "an instance of EmailService"
        def emailService = new EmailService(spyLogService)

        and: "an invoice instance"
        def invoice = new Invoice(totalAmount: BigDecimal.ONE)

        when: "an attempt tp send an email with an log is made"
        emailService.sendEmail(invoice)

        then: "the email is sent successfully"
        1 * spyLogService.log(invoice, 'Email sent')
    }
}
