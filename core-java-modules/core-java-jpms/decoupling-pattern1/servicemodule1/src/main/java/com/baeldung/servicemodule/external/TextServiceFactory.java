package com.baeldung.servicemodule.external;

import com.baeldung.servicemodule.internal.LowercaseTextService;
import com.baeldung.servicemodule.internal.UppercaseTextService;

public class TextServiceFactory {
    
    private TextServiceFactory() {}
    
    public static TextService getTextService(String name) {
        return name.equalsIgnoreCase("lowercase") ? new LowercaseTextService(): new UppercaseTextService();
    }
    
}
