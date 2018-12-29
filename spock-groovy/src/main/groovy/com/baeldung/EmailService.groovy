class EmailService {

    def logService

    EmailService(LogService logService) {
        this.logService = logService
    }

    def sendEmail(Invoice invoice) {
        if (invoice) {
            logService.log(invoice, 'Email sent')
            return true
        }
        return false
    }
}
