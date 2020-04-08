package com.baeldung.heaxagonal.arch.adapter;

import com.baeldung.heaxagonal.arch.domain.Domain;
import com.baeldung.heaxagonal.arch.port.InboundPort;
import com.baeldung.heaxagonal.arch.port.OutboundPortGet;
import com.baeldung.heaxagonal.arch.port.OutboundPortPrint;

public class InboundAdapter {
    public static void main(String[] args) {
        OutboundPortGet outboundPortGet = new OutboundAdapterGet();
        OutboundPortPrint outboundPortPrint = new OutboundAdapterPrint();

        InboundPort inboundPort = new Domain(outboundPortGet, outboundPortPrint);

        inboundPort.execute();
    }
}
