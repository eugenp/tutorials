package com.baeldung.methodmultiplereturnvalues;

import java.util.List;

class MultipleReturnValuesUsingTuples {

    static Tuple2<Coordinates, Double> getMostDistantPoint(List<Coordinates> coordinatesList,
                                                           Coordinates target) {

        return coordinatesList.stream()
            .map(coordinates -> new Tuple2<>(coordinates, coordinates.calculateDistance(target)))
            .max((d1, d2) -> Double.compare(d1.getSecond(), d2.getSecond()))
            .get();

    }

}
