package com.spaceprogram.bigcache;

import java.io.Serializable;
import java.util.Date;

/**
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 2:08:38 AM
 */
public class SomeObject implements Serializable {

    private String id;

    private Date someDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSomeDate() {
        return someDate;
    }

    public void setSomeDate(Date someDate) {
        this.someDate = someDate;
    }
}
