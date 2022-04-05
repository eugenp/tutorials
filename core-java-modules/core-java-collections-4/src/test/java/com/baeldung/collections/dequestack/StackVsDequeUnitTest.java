package com.baeldung.collections.dequestack;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

class StackVsDequeUnitTest {

    @Test
    void givenAStack_whenAccessByIndex_thenElementCanBeRead() {
        Stack<String> myStack = new Stack<>();
        myStack.push("I am the 1st element."); //index 0
        myStack.push("I am the 2nd element."); //index 1
        myStack.push("I am the 3rd element."); //index 2
        //access by index
        assertThat(myStack.get(0)).isEqualTo("I am the 1st element.");
    }

    @Test
    void givenAStack_whenIterate_thenFromBottomToTop() {
        Stack<String> myStack = new Stack<>();
        myStack.push("I am at the bottom.");
        myStack.push("I am in the middle.");
        myStack.push("I am at the top.");

        Iterator<String> it = myStack.iterator();

        assertThat(it).toIterable().containsExactly(
          "I am at the bottom.",
          "I am in the middle.",
          "I am at the top.");
    }

    @Test
    void givenAStack_whenAddOrRemoveByIndex_thenElementCanBeAddedOrRemoved() {
        Stack<String> myStack = new Stack<>();
        myStack.push("I am the 1st element.");
        myStack.push("I am the 3rd element.");

        assertThat(myStack.size()).isEqualTo(2);

        //insert by index
        myStack.add(1, "I am the 2nd element.");
        assertThat(myStack.size()).isEqualTo(3);
        assertThat(myStack.get(1)).isEqualTo("I am the 2nd element.");
        //remove by index
        myStack.remove(1);
        assertThat(myStack.size()).isEqualTo(2);
    }

    @Test
    void givenADeque_whenAddOrRemoveLastElement_thenTheLastElementCanBeAddedOrRemoved() {
        Deque<String> myStack = new ArrayDeque<>();
        myStack.push("I am the 1st element.");
        myStack.push("I am the 2nd element.");
        myStack.push("I am the 3rd element.");

        assertThat(myStack.size()).isEqualTo(3);

        //insert element to the bottom of the stack
        myStack.addLast("I am the NEW element.");
        assertThat(myStack.size()).isEqualTo(4);
        assertThat(myStack.peek()).isEqualTo("I am the 3rd element.");

        //remove element from the bottom of the stack
        String removedStr = myStack.removeLast();
        assertThat(myStack.size()).isEqualTo(3);
        assertThat(removedStr).isEqualTo("I am the NEW element.");
    }

    @Test
    void givenADeque_whenIterate_thenFromTopToBottom() {
        Deque<String> myStack = new ArrayDeque<>();
        myStack.push("I am at the bottom.");
        myStack.push("I am in the middle.");
        myStack.push("I am at the top.");

        Iterator<String> it = myStack.iterator();

        assertThat(it).toIterable().containsExactly(
          "I am at the top.",
          "I am in the middle.",
          "I am at the bottom.");
    }
}