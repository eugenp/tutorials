package com.baeldung.features;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.VectorSpecies;

public class JEP414 {

    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;


    public void newVectorComputation(float[] a, float[] b, float[] c) {
        for (var i = 0; i < a.length; i += SPECIES.length()) {
            var m = SPECIES.indexInRange(i, a.length);
            var va = FloatVector.fromArray(SPECIES, a, i, m);
            var vb = FloatVector.fromArray(SPECIES, b, i, m);
            var vc = va.mul(vb);
            vc.intoArray(c, i, m);
        }
    }

    public void commonVectorComputation(float[] a, float[] b, float[] c) {
        for (var i = 0; i < a.length; i ++) {
            c[i] = a[i] * b[i];
        }
    }

}
