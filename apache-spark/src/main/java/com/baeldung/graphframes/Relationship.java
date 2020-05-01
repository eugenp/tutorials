package com.baeldung.graphframes;

import java.io.Serializable;
import java.util.UUID;

public class Relationship implements Serializable {
    private String type;
    private String src;
    private String dst;
    private UUID id;

    public Relationship(String type, String src, String dst) {
        this.type = type;
        this.src = src;
        this.dst = dst;
        this.id = UUID.randomUUID();
    }

    public String getId() {
        return id.toString();
    }

    public String getType() {
        return type;
    }

    public String getSrc() {
        return src;
    }

    public String getDst() {
        return dst;
    }
    
    @Override
    public String toString() {
    	return getSrc() + " -- " + getType() + " --> " + getDst();
    }
}
