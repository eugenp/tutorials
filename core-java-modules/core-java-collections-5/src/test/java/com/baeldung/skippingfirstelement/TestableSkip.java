package com.baeldung.skippingfirstelement;

import java.util.List;

public interface TestableSkip {

    void reset();

    List<?> getResult();
}
