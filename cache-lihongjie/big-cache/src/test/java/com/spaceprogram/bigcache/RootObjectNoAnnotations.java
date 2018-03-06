package com.spaceprogram.bigcache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: treeder
 * Date: Oct 18, 2008
 * Time: 2:32:41 PM
 */
public class RootObjectNoAnnotations implements Serializable {

    private String id;

    private Date created;

    private SomeObject someObject;

    List<SomeObject2> others = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public SomeObject getSomeObject() {
        return someObject;
    }

    public void setSomeObject(SomeObject someObject) {
        this.someObject = someObject;
    }

    public List<SomeObject2> getOthers() {
        return others;
    }

    public void setOthers(List<SomeObject2> others) {
        this.others = others;
    }
}
