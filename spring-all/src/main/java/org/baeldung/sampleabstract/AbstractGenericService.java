package org.baeldung.sampleabstract;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractGenericService<T, S> {

    @Autowired
    private T genericFieldT;

    private S genericFieldS;

    public T getGenericFieldT() {

        return genericFieldT;
    }

    public void setGenericFieldT(T genericFieldT) {

        this.genericFieldT = genericFieldT;
    }

    public S getGenericFieldS() {

        return genericFieldS;
    }

    @Autowired
    public void setGenericFieldS(S genericFieldS) {

        this.genericFieldS = genericFieldS;
    }

    public void afterInitialize() {

        System.out.println(genericFieldT.getClass().getSimpleName());
        System.out.println(genericFieldS.getClass().getSimpleName());
    }
}
