package com.baeldung.geotools;

import static org.junit.Assert.assertNotNull;

import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeatureType;

public class GeoToolsUnitTest {

    @Test
    public void givenFeatureType_whenAddLocations_returnFeatureCollection() {

        DefaultFeatureCollection collection = new DefaultFeatureCollection();

        SimpleFeatureType CITY = ShapeFile.createFeatureType();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(CITY);

        ShapeFile.addLocations(featureBuilder, collection);

        assertNotNull(collection);

    }

}
