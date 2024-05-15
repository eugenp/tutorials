package com.baeldung.projection;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.baeldung.projection.model.InStock;
import com.baeldung.projection.model.Inventory;
import com.baeldung.projection.model.Size;

abstract class AbstractTestProjection {

    public List<Inventory> getInventories() {
        Inventory journal = new Inventory();
        journal.setItem("journal");
        journal.setStatus("A");
        journal.setSize(new Size(14.0, 21.0, "cm"));
        journal.setInStock(Collections.singletonList(new InStock("A", 5)));

        Inventory notebook = new Inventory();
        notebook.setItem("notebook");
        notebook.setStatus("A");
        notebook.setSize(new Size(8.5, 11.0, "in"));
        notebook.setInStock(Collections.singletonList(new InStock("C", 5)));

        Inventory paper = new Inventory();
        paper.setItem("paper");
        paper.setStatus("D");
        paper.setSize(new Size(8.5, 11.0, "in"));
        paper.setInStock(Collections.singletonList(new InStock("A", 60)));

        return Arrays.asList(journal, notebook, paper);
    }

    abstract void whenIncludeFields_thenOnlyIncludedFieldsAreNotNull();

    abstract void whenIncludeFieldsAndExcludeOtherFields_thenOnlyExcludedFieldsAreNull();

    abstract void whenIncludeAllButExcludeSomeFields_thenOnlyExcludedFieldsAreNull();

    abstract void whenIncludeEmbeddedFields_thenEmbeddedFieldsAreNotNull();

    abstract void whenExcludeEmbeddedFields_thenEmbeddedFieldsAreNull();

    abstract void whenIncludeEmbeddedFieldsInArray_thenEmbeddedFieldsInArrayAreNotNull();

    abstract void whenIncludeEmbeddedFieldsSliceInArray_thenArrayLengthEqualToSlice();

}
