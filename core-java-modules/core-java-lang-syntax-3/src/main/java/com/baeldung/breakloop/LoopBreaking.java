package com.baeldung.breakloop;

public class LoopBreaking {

    public String simpleBreak() {
        String result = "";
        for (int outerCounter = 0; outerCounter < 2; outerCounter++) {
            result += "outer" + outerCounter;
            for (int innerCounter = 0; innerCounter < 2; innerCounter++) {
                result += "inner" + innerCounter;
                if (innerCounter == 0) {
                    break;
                }
            }
        }
        return result;
    }

    public String labelBreak() {
        String result = "";
        myBreakLabel:
        for (int outerCounter = 0; outerCounter < 2; outerCounter++) {
            result += "outer" + outerCounter;
            for (int innerCounter = 0; innerCounter < 2; innerCounter++) {
                result += "inner" + innerCounter;
                if (innerCounter == 0) {
                    break myBreakLabel;
                }
            }
        }
        return result;
    }

    public String usingReturn() {
        String result = "";
        for (int outerCounter = 0; outerCounter < 2; outerCounter++) {
            result += "outer" + outerCounter;
            for (int innerCounter = 0; innerCounter < 2; innerCounter++) {
                result += "inner" + innerCounter;
                if (innerCounter == 0) {
                    return result;
                }
            }
        }
        return "failed";
    }
}
