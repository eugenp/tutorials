package com.baeldung.geotools;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ShapeFile {

    private static final String FILE_NAME = "shapefile.shp";

    public static void main(String[] args) throws Exception {

        DefaultFeatureCollection collection = new DefaultFeatureCollection();

        GeometryFactory geometryFactory =  JTSFactoryFinder.getGeometryFactory(null);

        SimpleFeatureType TYPE = DataUtilities.createType("Location", "location:Point:srid=4326," + "name:String");

        SimpleFeatureType CITY = createFeatureType();

        addLocations(CITY, collection);

        File shapeFile = getNewShapeFile();

        ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

        Map<String, Serializable> params = new HashMap<String, Serializable>();

        ShapefileDataStore dataStore = setDataStoreParams(dataStoreFactory, params, shapeFile, CITY);

        writeToFile(dataStore, collection);
    }

    static SimpleFeatureType createFeatureType() {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.setName("Location");
        builder.setCRS(DefaultGeographicCRS.WGS84);

        builder.add("Location", Point.class);
        builder.length(15)
          .add("Name", String.class);

        return builder.buildFeatureType();
    }

    static void addLocations(SimpleFeatureType CITY, DefaultFeatureCollection collection) {

        Map<String, List<Double>> locations = new HashMap<>();

        double lat = 13.752222;
        double lng = 100.493889;
        addToLocationMap("Bangkok", lat, lng, locations);

        lat = 53.083333;
        lng = -0.15;
        addToLocationMap("New York", lat, lng, locations);

        lat = -33.925278;
        lng = 18.423889;
        addToLocationMap("Cape Town", lat, lng, locations);

        lat = -33.859972;
        lng = 151.211111;
        addToLocationMap("Sydney", lat, lng, locations);

        lat = 45.420833;
        lng = -75.69;
        addToLocationMap("Ottawa", lat, lng, locations);

        lat = 30.07708;
        lng = 31.285909;
        addToLocationMap("Cairo", lat, lng, locations);

        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);

        locations.entrySet().stream()
          .map(toFeature(CITY, geometryFactory))
          .forEach(collection::add);
    }

    private static Function<Map.Entry<String, List<Double>>, SimpleFeature> toFeature(SimpleFeatureType CITY, GeometryFactory geometryFactory) {
        return location -> {
            Point point = geometryFactory.createPoint(
              new Coordinate(location.getValue()
                .get(0), location.getValue().get(1)));

            SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(CITY);
            featureBuilder.add(point);
            featureBuilder.add(location.getKey());
            return featureBuilder.buildFeature(null);
        };
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

        return chooser.getSelectedFile();
    }

    private static ShapefileDataStore setDataStoreParams(ShapefileDataStoreFactory dataStoreFactory, Map<String, Serializable> params, File shapeFile, SimpleFeatureType CITY) throws Exception {
        params.put("url", shapeFile.toURI()
          .toURL());
        params.put("create spatial index", Boolean.TRUE);

        ShapefileDataStore dataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
        dataStore.createSchema(CITY);

        return dataStore;
    }

    private static void writeToFile(ShapefileDataStore dataStore, DefaultFeatureCollection collection) throws Exception {

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
}
