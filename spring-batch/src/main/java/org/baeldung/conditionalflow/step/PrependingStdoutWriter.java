package org.baeldung.conditionalflow.step;

import java.io.OutputStream;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class PrependingStdoutWriter<T> implements ItemWriter<T> {
    private String prependText;
    private OutputStream writeTo;

    public PrependingStdoutWriter(String prependText, OutputStream os) {
        this.prependText = prependText;
        this.writeTo = os;
    }

    public PrependingStdoutWriter(String prependText) {
        this(prependText, System.out);
    }

    @Override
    public void write(List<? extends T> list) throws Exception {
        for (T listItem : list) {
            System.out.println(prependText + " " + listItem.toString());
        }
    }
}
