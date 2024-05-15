package com.baeldung.geotools;

import static org.junit.Assert.assertNotNull;

import org.geotools.feature.DefaultFeatureCollection;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeatureType;

public class GeoToolsUnitTest {

    @Test
    public void givenFeatureType_whenAddLocations_returnFeatureCollection() {
        DefaultFeatureCollection collection = new DefaultFeatureCollection();

        SimpleFeatureType CITY = ShapeFile.createFeatureType();

        ShapeFile.addLocations(CITY, collection);

        assertNotNull(collection);
    }
    
}
