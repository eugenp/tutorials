package com.baeldung.geotools;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultTransaction;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class ShapeFile {

    private static final String FILE_NAME = "shapefile.shp";

    public static void main(String[] args) throws Exception {

        DefaultFeatureCollection collection = new DefaultFeatureCollection();

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        SimpleFeatureType TYPE = DataUtilities.createType("Location", "location:Point:srid=4326," + "name:String");

        SimpleFeatureType CITY = createFeatureType();

        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(CITY);

        addLocations(featureBuilder, collection);

        File shapeFile = getNewShapeFile();

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

        Map<String, Serializable> params = new HashMap<String, Serializable>();
        params.put("url", shapeFile.toURI()
            .toURL());
        params.put("create spatial index", Boolean.TRUE);

        ShapefileDataStore dataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
        dataStore.createSchema(CITY);

        // If you decide to use the TYPE type and create a Data Store with it,
        // You will need to uncomment this line to set the Coordinate Reference System
        // newDataStore.forceSchemaCRS(DefaultGeographicCRS.WGS84);

        Transaction transaction = new DefaultTransaction("create");

        String typeName = dataStore.getTypeNames()[0];
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);

        if (featureSource instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(collection);
                transaction.commit();

            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();

            } finally {
                transaction.close();
            }
            System.exit(0); // success!
        } else {
            System.out.println(typeName + " does not support read/write access");
            System.exit(1);
        }

    }

    public static SimpleFeatureType createFeatureType() {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84);

        builder.add("Location", Point.class);
        builder.length(15)
            .add("Name", String.class);

        SimpleFeatureType CITY = builder.buildFeatureType();

        return CITY;
    }

    public static void addLocations(SimpleFeatureBuilder featureBuilder, DefaultFeatureCollection collection) {

        Map<String, List<Double>> locations = new HashMap<>();

        double lat = 13.752222;
        double lng = 100.493889;
        String name = "Bangkok";
        addToLocationMap(name, lat, lng, locations);

        lat = 53.083333;
        lng = -0.15;
        name = "New York";
        addToLocationMap(name, lat, lng, locations);

        lat = -33.925278;
        lng = 18.423889;
        name = "Cape Town";
        addToLocationMap(name, lat, lng, locations);

        lat = -33.859972;
        lng = 151.211111;
        name = "Sydney";
        addToLocationMap(name, lat, lng, locations);

        lat = 45.420833;
        lng = -75.69;
        name = "Ottawa";
        addToLocationMap(name, lat, lng, locations);

        lat = 30.07708;
        lng = 31.285909;
        name = "Cairo";
        addToLocationMap(name, lat, lng, locations);

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        for (Map.Entry<String, List<Double>> location : locations.entrySet()) {
            Point point = geometryFactory.createPoint(new Coordinate(location.getValue()
                .get(0),
                location.getValue()
                    .get(1)));
            featureBuilder.add(point);
            featureBuilder.add(name);
            SimpleFeature feature = featureBuilder.buildFeature(null);
            collection.add(feature);
        }

    }
    
    private static void addToLocationMap(String name, double lat, double lng, Map<String, List<Double>> locations) {
        List<Double> coordinates = new ArrayList<>();

        coordinates.add(lat);
        coordinates.add(lng);
        locations.put(name, coordinates);
    }

    private static File getNewShapeFile() {
        String filePath = new File(".").getAbsolutePath() + FILE_NAME;
        

        JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");
        chooser.setDialogTitle("Save shapefile");
        chooser.setSelectedFile(new File(filePath));

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal != JFileDataStoreChooser.APPROVE_OPTION) {
            System.exit(0);
        }

        File shapeFile = chooser.getSelectedFile();
        if (shapeFile.equals(filePath)) {
            System.out.println("Error: cannot replace " + filePath);
            System.exit(0);
        }

        return shapeFile;
    }

}
