package com.baeldung.batch.understanding;

import javax.batch.api.chunk.AbstractCheckpointAlgorithm;
import javax.inject.Named;

@Named
public class CustomCheckPoint extends AbstractCheckpointAlgorithm {
    @Override
    public boolean isReadyToCheckpoint() throws Exception {
        return SimpleChunkItemReader.COUNT % 5 == 0;
    }
}
