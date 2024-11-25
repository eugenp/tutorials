package com.baeldung.methodmultiplereturnvalues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MultipleReturnValuesUsingCollections {

    static List<Number> getCoordinatesList() {

        List<Number> coordinates = new ArrayList<>();

        coordinates.add(10);
        coordinates.add(12.5);

        return coordinates;
    }

    static Map<String, Number> getCoordinatesMap() {

        Map<String, Number> coordinates = new HashMap<>();
        
        coordinates.put("longitude", 10);
        coordinates.put("latitude", 12.5);

        return coordinates;
    }

}
