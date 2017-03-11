class SpockTutorial {

    def "numbers to the power of two"(int a, int b, int c) {
        expect:
        Math.pow(a, b) == c

        where:
        a | b | c
        1 | 2 | 1
        2 | 2 | 4
        3 | 2 | 9
    }

    def "Should return default value for mock"() {
        given:
        def paymentGateway = Mock(PaymentGateway)

        when:
        def result = paymentGateway.makePayment()

        then:
        result == true
    }
}
