package com.baeldung.methodmultiplereturnvalues;

import java.util.List;

class MultipleReturnValuesUsingTuples {

    static Tuple2<Coordinates, Double> getMostDistantPoint(List<Coordinates> coordinatesList,
                                                           Coordinates target) {

        return coordinatesList.stream()
            .map(coor -> new Tuple2<>(coor, coor.calculateDistance(target)))
            .max((d1, d2) -> Double.compare(d1.getSecond(), d2.getSecond()))
            .get();

    }

}
