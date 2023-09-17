package com.baeldung.unnamed.variables;

import java.util.Collection;

public class UnnamedVariables {

    static int countCarsOverLimitWithNamedVariable(Collection<Car> cars, int limit) {
        var total = 0;
        var totalOverLimit = 0;
        for (var car : cars) {
            total++;
            if (total > limit) {
                totalOverLimit++;
                // side effect
            }
        }
        return totalOverLimit;
    }

    static int countCarsOverLimitWithUnnamedVariable(Collection<Car> cars, int limit) {
        var total = 0;
        var totalOverLimit = 0;
        for (var _ : cars) {
            total++;
            if (total > limit) {
                totalOverLimit++;
                // side effect
            }
        }
        return totalOverLimit;
    }
}
