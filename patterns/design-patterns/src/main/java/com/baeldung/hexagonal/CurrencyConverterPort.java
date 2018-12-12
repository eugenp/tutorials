package core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CurrencyConverterPort {

    BigDecimal convert(String fromCurrencyCode, String toCurrencyCode, BigDecimal value, LocalDateTime conversionDateTime);
}
