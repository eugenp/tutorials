package com.baeldung.methodmultiplereturnvalues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MultipleReturnValuesUsingCollections {

    List<Double> getCoordinatesList() {

        List<Double> coordinates = new ArrayList<>();

        coordinates.add(10.2);
        coordinates.add(12.5);

        return coordinates;
    }

    Map<String, Double> getCoordinatesMap() {

        Map<String, Double> coordinates = new HashMap<>();
        
        coordinates.put("longitude", 10.2);
        coordinates.put("latitude", 12.5);

        return coordinates;
    }

}
