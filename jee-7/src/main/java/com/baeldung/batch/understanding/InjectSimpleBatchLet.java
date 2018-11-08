package com.baeldung.batch.understanding;

import javax.batch.api.AbstractBatchlet;
import javax.batch.api.BatchProperty;
import javax.batch.runtime.BatchStatus;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InjectSimpleBatchLet extends AbstractBatchlet {
    @Inject
    @BatchProperty(name = "name")
    private String nameString;

    @Override
    public String process() throws Exception {
        System.out.println("Value passed in = " + nameString);
        return BatchStatus.FAILED.toString();
    }
}
