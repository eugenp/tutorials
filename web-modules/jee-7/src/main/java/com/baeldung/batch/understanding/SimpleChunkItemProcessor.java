package com.baeldung.batch.understanding;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class SimpleChunkItemProcessor implements ItemProcessor {
    @Override
    public Integer processItem(Object t) {
        return ((Integer) t).intValue() % 2 == 0 ? null : ((Integer) t).intValue();
    }
}
