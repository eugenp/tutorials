import spock.lang.Specification

class EmailServiceSpec extends Specification {

    def "Should log the invoice when the email is successfully sent to the client"() {

        given: "an invoice"
        def invoice = new Invoice()

        and: "a mock of LogService"
        def logService = Mock(LogService)

        and: "an instance of EmailService"
        def emailService = new EmailService(logService)

        when: "an attempt to send an email with an log is made"
        def emailSent = emailService.sendEmail(invoice)

        then: "the correct overloaded log method is invoked"
        1 * logService.log(invoice, 'Email sent')

        and: "the email is sent"
        emailSent
    }

    def "Should return true if the email is sent successfully to the client"() {

        given: "an log"
        def invoice = new Invoice()

        and: "a fake LogService"
        def logService = Stub(LogService)

        logService.log(invoice, 'Email sent') >> {}

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
        def invoice = new Invoice()

        when: "an attempt tp send an email with an log is made"
        emailService.sendEmail(invoice)

        then: "the email is sent successfully"
        1 * spyLogService.log(invoice, 'Email sent')
    }
}
