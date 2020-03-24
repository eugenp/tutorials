package com.baeldung.lombok.getter;

import lombok.Getter;

/**
 * Related Article Sections:
 * 3.1. A boolean Field Having the Same Name With Its Accessor
 *
 */
public class GetterBooleanSameAccessor {
    @Getter
    private boolean isRunning = true;
}
