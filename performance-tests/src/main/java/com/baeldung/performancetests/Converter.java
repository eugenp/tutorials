package com.baeldung.performancetests;

import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.source.SourceCode;
import com.baeldung.performancetests.model.source.SourceOrder;
import com.baeldung.performancetests.model.destination.Order;

public interface Converter {
    Order convert(SourceOrder sourceOrder);
    DestinationCode convert(SourceCode sourceCode);
}
