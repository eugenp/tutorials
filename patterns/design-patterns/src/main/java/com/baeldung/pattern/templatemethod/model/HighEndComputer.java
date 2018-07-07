package com.baeldung.pattern.templatemethod.model;

import com.baeldung.pattern.templatemethod.model.Computer;
import java.util.Map;

public class HighEndComputer extends Computer {
    
    public HighEndComputer(Map<String, String> computerParts) {
        super(computerParts);
    }
}
