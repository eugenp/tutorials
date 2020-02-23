package com.baeldung.methodmultiplereturnvalues;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.List;
import java.util.stream.Collectors;

class MultipleReturnValuesUsingApacheCommonsTriple {

    static ImmutableTriple<Double, Double, Double> getMinAvgMaxTriple(
      List<Coordinates> coordinatesList,
      Coordinates target) {

        List<Double> distanceList = coordinatesList.stream()
          .map(coordinates -> coordinates.calculateDistance(target))
          .collect(Collectors.toList());
        Double minDistance = distanceList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        Double avgDistance = distanceList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0D);
        Double maxDistance = distanceList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();

        return ImmutableTriple.of(minDistance, avgDistance, maxDistance);
    }
}
