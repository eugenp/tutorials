package com.baeldung.heaxagonal.arch.adapter;

import com.baeldung.heaxagonal.arch.port.OutboundPortPrint;

import java.util.Objects;

public class OutboundAdapterPrint implements OutboundPortPrint {
    @Override
    public void execute(String[] strings) {
        Objects.requireNonNull(strings);
        for (int i = 0; i < strings.length; i++) {
            System.out.println(strings[i]);
        }
    }
}
