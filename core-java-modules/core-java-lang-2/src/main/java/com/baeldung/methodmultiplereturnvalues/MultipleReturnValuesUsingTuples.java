package com.baeldung.methodmultiplereturnvalues;

import java.util.List;

class MultipleReturnValuesUsingTuples {

    static Tuple2<Integer, Coordinates> getFirstNullNearbyLocation(List<Coordinates> coordinatesList) {

        int id = 55;
        return coordinatesList.stream()
            .filter(coordinates -> coordinates.getNearbyLocation() == null)
            .map(coordinates -> new Tuple2<Integer, Coordinates>(id, coordinates))
            .findFirst()
            .get();

    }

}
