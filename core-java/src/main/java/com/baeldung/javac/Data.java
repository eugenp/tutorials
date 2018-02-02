package com.baeldung.javac;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
    static List<String> textList = new ArrayList();

    private static void addText() {
        textList.add("baeldung");
        textList.add(".");
        textList.add("com");
    }

    public List getTextList() {
        this.addText();
        List<String> result = new ArrayList<String>();
        String firstElement = (String) textList.get(0);
        switch (firstElement) {
        case "baeldung":
            result.add("baeldung");
        case "com":
            result.add("com");
        }
        return result;
    }
}
