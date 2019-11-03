package com.baeldung.algorithms.twopointertechnique;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LinkedListFindMiddleUnitTest {

    LinkedListFindMiddle linkedListFindMiddle = new LinkedListFindMiddle();

    @Test
    public void givenLinkedListOfMyNodes_whenLinkedListFindMiddle_thenCorrect() {

        MyNode<String> head = createNodesList(8);

        assertThat(linkedListFindMiddle.findMiddle(head)).isEqualTo("4");

        head = createNodesList(9);

        assertThat(linkedListFindMiddle.findMiddle(head)).isEqualTo("5");
    }

    private static MyNode<String> createNodesList(int n) {

        MyNode<String> head = new MyNode<String>("1");
        MyNode<String> current = head;

        for (int i = 2; i <= n; i++) {
            MyNode<String> newNode = new MyNode<String>(String.valueOf(i));
            current.setNext(newNode);
            current = newNode;
        }

        return head;
    }

}
