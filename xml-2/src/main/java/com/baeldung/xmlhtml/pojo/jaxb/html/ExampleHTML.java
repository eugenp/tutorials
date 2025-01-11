package com.baeldung.xmlhtml.pojo.jaxb.html;

import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Body;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Meta;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"head", "body"})
@XmlRootElement(name = "html")
public class ExampleHTML {

    private List<Meta> head = new ArrayList<>();

    private Body body;

    public ExampleHTML() { }

    public List<Meta> getHead() {
        return head;
    }

    @XmlElementWrapper(name = "head")
    @XmlElement(name = "meta")
    public void setHead(List<Meta> head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    @XmlElement(name = "body")
    public void setBody(Body body) {
        this.body = body;
    }
}
