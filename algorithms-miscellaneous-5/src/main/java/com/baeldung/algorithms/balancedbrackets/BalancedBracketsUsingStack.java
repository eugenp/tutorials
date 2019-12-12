package com.baeldung.algorithms.balancedbrackets;

import java.util.Stack;

public class BalancedBracketsUsingStack {

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

                Stack<Character> stack = new Stack<>();
                for (char ch: str.toCharArray()) {
                        if (ch == '{' || ch == '[' || ch == '(') {
                                stack.push(ch);
                        } else {
                                if ( !stack.isEmpty()
                                        && ((stack.peek() == '{' && ch == '}')
                                        || (stack.peek() == '[' && ch == ']')
                                        || (stack.peek() == '(' && ch == ')')
                                    )) {
                                        stack.pop();
                                } else {
                                        return false;
                                }
                        }
                }

                return true;
        }
}