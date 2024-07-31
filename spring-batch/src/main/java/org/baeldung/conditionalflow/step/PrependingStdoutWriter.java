package org.baeldung.conditionalflow.step;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class PrependingStdoutWriter<T> implements ItemWriter<T> {
    private String prependText;

    public PrependingStdoutWriter(String prependText) {
        this.prependText = prependText;
    }

    @Override
    public void write(Chunk<? extends T> chunk) {
        for (T listItem : chunk) {
            System.out.println(prependText + " " + listItem.toString());
        }
    }
}
