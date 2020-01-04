package com.baeldung.algorithms.balancedbrackets;

import java.util.Deque;
import java.util.LinkedList;

public class BalancedBracketsUsingDeque {

        public boolean isBalanced(String str) {
                if (null == str || str.length() == 0 || ((str.length() % 2) != 0)) {
                        return false;
                } else {
                        char[] ch = str.toCharArray();
                        for (char c : ch) {
                                if (!(c == '{' || c == '[' || c == '(' || c == '}' || c == ']' || c == ')')) {
                                        return false;
                                }

                        }
                }

                Deque<Character> deque = new LinkedList<>();
                for (char ch: str.toCharArray()) {
                        if (ch == '{' || ch == '[' || ch == '(') {
                                deque.addFirst(ch);
                        } else {
                                if ( !deque.isEmpty()
                                        && ((deque.peekFirst() == '{' && ch == '}')
                                        || (deque.peekFirst() == '[' && ch == ']')
                                        || (deque.peekFirst() == '(' && ch == ')')
                                    )) {
                                        deque.removeFirst();
                                } else {
                                        return false;
                                }
                        }
                }

                return true;
        }
}