package org.baeldung.conditionalflow.step;

import org.springframework.batch.item.ItemWriter;

import java.io.OutputStream;
import java.util.List;

public class PrependingStdoutWriter<T> implements ItemWriter<T> {
    private String prependText;
    private OutputStream writeTo;

    private PrependingStdoutWriter() {
    }

    public PrependingStdoutWriter(String prependText, OutputStream os){
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
