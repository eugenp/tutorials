package com.baeldung.jstl.bundles;

import java.util.ListResourceBundle;

public class CustomMessage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    static final Object[][] contents = {
            {"verb.go", "go"},
            {"verb.come", "come"},
            {"verb.sit", "sit"},
            {"verb.stand", "stand"}
    };
}
