package com.baeldung.batch.understanding;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class SimpleChunkWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> items) throws Exception {
    }
}
