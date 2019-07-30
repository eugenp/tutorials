package io.sirix.tutorial.xml;

import java.util.Objects;

import org.sirix.api.xml.XmlNodeReadOnlyTrx;
import org.sirix.diff.DiffDepth;
import org.sirix.diff.DiffFactory.DiffType;
import org.sirix.diff.DiffObserver;

public class MyXmlDiffObserver implements DiffObserver {

    private final XmlNodeReadOnlyTrx firstRtx;

    private final XmlNodeReadOnlyTrx secondRtx;

    MyXmlDiffObserver(final XmlNodeReadOnlyTrx newRtx, final XmlNodeReadOnlyTrx oldRtx) {
        this.firstRtx = Objects.requireNonNull(newRtx, "The first read only trx must not be null");
        this.secondRtx = Objects.requireNonNull(oldRtx, "The second read only trx must not be null");
    }

    @Override
    public void diffListener(DiffType diffType, long newNodeKey, long oldNodeKey, DiffDepth depth) {
        switch (diffType) {
        case INSERTED:
            System.out.println("DiffType: " + diffType);

            firstRtx.moveTo(newNodeKey);
            printNode(firstRtx);

            break;
        case DELETED:
            System.out.println("DiffType: " + diffType);

            secondRtx.moveTo(oldNodeKey);
            printNode(secondRtx);

            break;
        case UPDATED:
            System.out.println("DiffType: " + diffType);

            secondRtx.moveTo(oldNodeKey);
            System.out.print("Old: ");
            printNode(secondRtx);

            firstRtx.moveTo(newNodeKey);
            System.out.print("New: ");
            printNode(firstRtx);

            break;
        default:
        }
    }

    private void printNode(final XmlNodeReadOnlyTrx rtx) {
        switch (rtx.getKind()) {
        case ELEMENT:
            System.out.println(rtx.getName());
            break;
        case TEXT:
            System.out.println(rtx.getValue());
            break;
        case COMMENT:
            System.out.println(rtx.getValue());
            break;
        case PROCESSING_INSTRUCTION:
            System.out.println(rtx.getName());
            break;
        case ATTRIBUTE:
            System.out.println(rtx.getName());
            break;
        case NAMESPACE:
            System.out.println(rtx.getName());
            break;
        default:
            throw new IllegalStateException("Kind not known.");
        }
    }

    @Override
    public void diffDone() {
    }
}
