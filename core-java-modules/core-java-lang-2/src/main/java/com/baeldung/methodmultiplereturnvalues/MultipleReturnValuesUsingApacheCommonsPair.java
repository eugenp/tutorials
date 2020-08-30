package com.baeldung.methodmultiplereturnvalues;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.List;

class MultipleReturnValuesUsingApacheCommonsPair {

    static ImmutablePair<Coordinates, Double> getMostDistantPoint(
      List<Coordinates> coordinatesList,
      Coordinates target) {
        return coordinatesList.stream()
          .map(coordinates -> ImmutablePair.of(coordinates, coordinates.calculateDistance(target)))
          .max(Comparator.comparingDouble(Pair::getRight))
          .get();

    }
}
