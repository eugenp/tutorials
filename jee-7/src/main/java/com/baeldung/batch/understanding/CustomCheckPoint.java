package com.baeldung.batch.understanding;

import javax.batch.api.chunk.AbstractCheckpointAlgorithm;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class CustomCheckPoint extends AbstractCheckpointAlgorithm {
    
    @Inject
    JobContext jobContext;
    
    private Integer counterRead = 0;
    
    @Override
    public boolean isReadyToCheckpoint() throws Exception {
        counterRead = (Integer)jobContext.getTransientUserData();
        System.out.println("counterRead : " + counterRead);
        return counterRead % 5 == 0;
    }
}
