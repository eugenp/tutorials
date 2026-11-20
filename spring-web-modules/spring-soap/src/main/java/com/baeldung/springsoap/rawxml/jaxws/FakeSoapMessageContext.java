package com.baeldung.springsoap.rawxml.jaxws;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public final class FakeSoapMessageContext extends HashMap<String, Object> implements SOAPMessageContext {

    private SOAPMessage message;

    FakeSoapMessageContext(SOAPMessage message, boolean outbound) {
        this.message = message;
        put(MessageContext.MESSAGE_OUTBOUND_PROPERTY, outbound);
    }

    @Override
    public SOAPMessage getMessage() {
        return message;
    }

    @Override
    public void setMessage(SOAPMessage message) {
        this.message = message;
    }

    @Override
    public Object[] getHeaders(QName header, JAXBContext context, boolean allRoles) {
        return new Object[0];
    }

    @Override
    public Set<String> getRoles() {
        return Collections.emptySet();
    }

    @Override
    public void setScope(String name, Scope scope) {
    }

    @Override
    public Scope getScope(String name) {
        return Scope.APPLICATION;
    }
}