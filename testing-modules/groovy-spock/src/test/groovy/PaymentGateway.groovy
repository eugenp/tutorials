interface PaymentGateway {
    boolean makePayment(BigDecimal amount)
}