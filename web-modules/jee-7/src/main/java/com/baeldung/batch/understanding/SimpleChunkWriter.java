package com.baeldung.batch.understanding;

import java.util.ArrayList;
import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class SimpleChunkWriter extends AbstractItemWriter {
    List<Integer> processed = new ArrayList<>();
    @Override
    public void writeItems(List<Object> items) throws Exception {
        items.stream().map(Integer.class::cast).forEach(this.processed::add);
    }
}
