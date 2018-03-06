package com.spaceprogram.bigcache;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 1:43:53 AM
 */
@XmlRootElement
public class RootObject implements Serializable {

    private String id;

    private Date created;

    private SomeObject someObject;

    List<SomeObject2> others = new ArrayList<>();

    public RootObject() {
        someObject = new SomeObject();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SomeObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(SomeObject someObject) {
        this.someObject = someObject;
    }

    public void addOther(SomeObject2 someObject2) {
        others.add(someObject2);
    }

    public List<SomeObject2> getOthers() {
        return others;
    }

    public void setOthers(List<SomeObject2> others) {
        this.others = others;
    }
}
