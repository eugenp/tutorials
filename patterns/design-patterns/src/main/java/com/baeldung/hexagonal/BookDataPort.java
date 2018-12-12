package core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface BookDataPort {

    BigDecimal getPrice(String isbn, LocalDateTime dateTimeOfPrice);

    void reserveOne(String isbn);
}
