package core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

interface BookServicePort {

    boolean orderBook(String isbn, BigDecimal paidAmount, String paymentCurrencyCode, LocalDateTime paymentDateTime);
}
