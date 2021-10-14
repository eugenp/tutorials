package com.baeldung.initializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class SimpleXstreamInitializer {

    public XStream getXstreamInstance() {
        XStream xstream = new XStream();
        xstream.allowTypesByWildcard(new String[]{
                "com.baeldung.**"
        });
        return xstream;
    }

    public XStream getXstreamJettisonMappedInstance() {
        return new XStream(new JettisonMappedXmlDriver());
    }

    public XStream getXstreamJsonHierarchicalInstance() {
        return new XStream(new JsonHierarchicalStreamDriver());
    }
}