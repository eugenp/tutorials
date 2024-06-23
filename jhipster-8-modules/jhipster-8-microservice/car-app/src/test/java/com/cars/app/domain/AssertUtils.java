package com.cars.app.domain;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

public class AssertUtils {

    public static Comparator<ZonedDateTime> zonedDataTimeSameInstant = Comparator.nullsFirst(
        (e1, a2) -> e1.withZoneSameInstant(ZoneOffset.UTC).compareTo(a2.withZoneSameInstant(ZoneOffset.UTC))
    );

    public static Comparator<BigDecimal> bigDecimalCompareTo = Comparator.nullsFirst((e1, a2) -> e1.compareTo(a2));
}
