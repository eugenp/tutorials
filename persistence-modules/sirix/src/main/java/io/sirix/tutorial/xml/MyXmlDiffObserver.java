package io.sirix.tutorial.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.sirix.diff.DiffDepth;
import org.sirix.diff.DiffFactory.DiffType;
import org.sirix.diff.DiffObserver;
import org.sirix.diff.DiffTuple;

public class MyXmlDiffObserver implements DiffObserver {

    private final List<DiffTuple> diffTuples;

    MyXmlDiffObserver() {
        diffTuples = new ArrayList<>();
    }

    @Override
    public void diffListener(DiffType diffType, long newNodeKey, long oldNodeKey, DiffDepth depth) {
        if (diffType == DiffType.INSERTED || diffType == DiffType.DELETED || diffType == DiffType.UPDATED)
            diffTuples.add(new DiffTuple(diffType, newNodeKey, oldNodeKey, depth));
    }

    @Override
    public void diffDone() {
    }

    public List<DiffTuple> getDiffTuples() {
        return Collections.unmodifiableList(diffTuples);
    }
}
