package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class NodeCopyUnitTest {

    @SuppressWarnings("unchecked") //
    @Test
    void givenRootObjectWithCiclycReferences_ThenDeepCopyWillReplaceAllObjectsWithCopies() throws Exception {
        Node<String> root = new Node<>("1");
        root.next = new Node<>("2");
        root.next.next = new Node<>("3", root);

        Node<String> copy = (Node<String>) DeepCloner.deepCopy(root);

        assertNotSame(root.value, copy.value);
        assertNotSame(root.next.value, copy.next.value);
        assertEquals(root.next.value, copy.next.value);
        assertNotSame(root.next, copy.next);
        assertSame(copy, copy.next.next.next);
    }

    @Test
    void givenRootObjectWithCiclycReferences_ThenShallowCopyWillChangeTheOriginalObject() {
        Node<String> root = new Node<>("1");
        Node<String> next = new Node<>("2", root);
        root.next = next;

        assertSame(root, next.next);

        Node<String> copy = root.copy();

        assertNotSame(root, next.next);
        assertSame(copy, next.next);
    }

    @Test
    void givenRootObjectWithCiclycReferences_ThenShallowCopyWillReplaceThemWithItself() {
        Node<String> root = new Node<>("1");
        root.next = new Node<>("2");
        root.next.next = new Node<>("3", root);

        Node<String> copy = root.copy();

        assertSame(root.value, copy.value);
        assertSame(root.next.value, copy.next.value);
        assertSame(root.next, copy.next);
        assertSame(copy, copy.next.next.next);
        assertNotSame(root, root.next.next.next);
        assertSame(copy, root.next.next.next);
    }

}
