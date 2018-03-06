package com.spaceprogram.bigcache;

import java.io.Serializable;

/**
 * User: treeder
 * Date: Oct 6, 2008
 * Time: 10:08:52 PM
 */
public class ClassWithEnums implements Serializable {
    private String id;
    private String date;
    private SomeEnum someEnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SomeEnum getSomeEnum() {
        return someEnum;
    }

    public void setSomeEnum(SomeEnum someEnum) {
        this.someEnum = someEnum;
    }
}
