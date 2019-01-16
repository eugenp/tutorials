package com.baeldung.xmlhtml.pojo.jaxb.html;

import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Body;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Meta;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam I. Gerard
 *
 * FullHTML POJO.
 */

/**
 * @XmlAccessorType - Specifies whether the fields are
 * nested or element attributes:
 *
 * <element>
 *     <element></element>
 * </element>
 *
 * or:
 *
 * <element attribute="me"></element>
 *
 * @XmlType - Specifies order.
 */

@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(propOrder = { "head", "body" })
@XmlRootElement(name="html")
public class FullHTML {

    /**
     * If a wrapper (annotated collection) is used,
     * it's better to set it directly into a parent
     * element. Otherwise, it can be encapsulated into
     * its own POJO.
     */

    private List<Meta> head = new ArrayList<>();

    /**
     * Here, the element is encapsulated into
     * its own POJO.
     */

    private Body body;

    public FullHTML () {}

    public List<Meta> getHead() {
        return head;
    }

    /**
     * Must be placed here.
     */

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
