package com.baeldung.pattern.templatemethod.model;

import com.baeldung.pattern.templatemethod.model.Computer;
import java.util.Map;

public class StandardComputer extends Computer {
    
    public StandardComputer(Map<String, String> computerParts) {
        super(computerParts);
    }
}
