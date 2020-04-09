package com.baeldung.map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyKey {
    private static final Logger LOG = LoggerFactory.getLogger(MyKey.class);
    
    private String name;
    private int id;

    public MyKey(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        LOG.debug("Calling hashCode()");
        return id;
    }

    @Override
    public String toString() {
        return "MyKey [name=" + name + ", id=" + id + "]";
    }

    @Override
    public boolean equals(Object obj) {
        LOG.debug("Calling equals() for key: " + obj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MyKey other = (MyKey) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
