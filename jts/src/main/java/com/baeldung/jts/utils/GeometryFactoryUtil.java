package com.baeldung.jts.utils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;

public class GeometryFactoryUtil {
    public static Geometry readWKT(String wkt) throws Exception {
        WKTReader reader = new WKTReader();
        return reader.read(wkt);
    }
}
