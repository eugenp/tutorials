class Invoice {

    BigDecimal totalAmount

    def eligibleForWithdraw() {
        return totalAmount.compareTo(BigDecimal.ZERO) > 0
    }
}
