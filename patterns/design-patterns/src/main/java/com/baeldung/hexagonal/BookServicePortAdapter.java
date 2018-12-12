package core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class BookServicePortAdapter implements BookServicePort {

    private BookDataPort bookDataPort;
    private CurrencyConverterPort currencyConverterPort;

    // constructor(s)

    @Override
    public boolean orderBook(String isbn, BigDecimal paidAmount, String paymentCurrencyCode, LocalDateTime paymentDateTime) {

        BigDecimal actualPrice = bookDataPort.getPrice(isbn, paymentDateTime);

        if (!paymentCurrencyCode.equalsIgnoreCase("usd")) {
            paidAmount = currencyConverterPort.convert("usd", paymentCurrencyCode, paidAmount, paymentDateTime);
        }

        if (!actualPrice.equals(paidAmount)) {
            return false;
        }

        bookDataPort.reserveOne(isbn);

        return true;
    }
}
