package com.baeldung.heaxagonal.arch.adapter;

import com.baeldung.heaxagonal.arch.port.OutboundPortGet;

public class OutboundAdapterGet implements OutboundPortGet {
    @Override
    public String[] execute() {
        String[] strings = { "data1", "data2", "data3", "data4" };
        return strings;
    }
}
