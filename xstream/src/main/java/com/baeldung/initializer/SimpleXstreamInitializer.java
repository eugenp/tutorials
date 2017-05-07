package com.baeldung.initializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class SimpleXstreamInitializer {

    public XStream getXstreamInstance() {
        return new XStream();
    }

    public XStream getXstreamJettisonMappedInstance() {
        return new XStream(new JettisonMappedXmlDriver());
    }

    public XStream getXstreamJsonHierarchicalInstance() {
        return new XStream(new JsonHierarchicalStreamDriver());
    }
}