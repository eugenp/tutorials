package com.baeldung.algorithms.balancedbrackets;

public class BalancedBracketsUsingString {

        public boolean isBalanced(String str) {
                boolean result = true;

                if (null == str || str.length() == 0 || ((str.length() % 2) != 0)) {
                        result = false;
                } else {
                        char[] ch = str.toCharArray();
                        for(char c : ch) {
                                if(!(c == '{' || c == '[' || c == '(' || c == '}' || c == ']' || c == ')')) {
                                        result = false;
                                        break;
                                }

                        }
                }

                if (result) {
                        while (str.indexOf("()") >= 0 || str.indexOf("[]") >= 0 || str.indexOf("{}") >= 0) {
                                str = str.replaceAll("\\(\\)", "")
                                    .replaceAll("\\[\\]", "")
                                    .replaceAll("\\{\\}", "");
                        }
                        if (str.length() > 0) {
                                result = false;
                        } else {
                                result = true;
                        }
                }

                return result;
        }

} 