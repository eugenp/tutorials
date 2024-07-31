package com.baeldung.jstl.bundles;

import java.util.ListResourceBundle;

public class CustomMessage_fr_FR extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    static final Object[][] contents = {
            {"verb.go", "aller"},
            {"verb.come", "venir"},
            {"verb.sit", "siéger"},
            {"verb.stand", "se lever"}
    };
}
