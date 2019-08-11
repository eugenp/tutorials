package com.baeldung.prototype;

import java.util.Map;

public class ShapeCache {

    private final Map<String,Shape> shapeCache;

    public ShapeCache(Map<String, Shape> shapeCache) {
        this.shapeCache = shapeCache;
    }

    public Shape getShape(String name){
        if(shapeCache.get(name)!=null) {
            return shapeCache.get(name);
        }
        throw new IllegalArgumentException();
    }
}
