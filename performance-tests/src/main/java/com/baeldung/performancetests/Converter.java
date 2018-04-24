package com.baeldung.performancetests;

import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;

public interface Converter {
    Order convert(SourceOrder sourceOrder);
}
