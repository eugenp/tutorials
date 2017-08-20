package com.baeldung.designpatterns.bridge;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class Blue implements Color {

    @Override
    public void fillColor() { 
        LOG.info("Color : Blue");
    }

}
