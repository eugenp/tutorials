package com.baeldung.javac;

import java.util.ArrayList;
import java.util.List;

public class Data {
    List<String> textList = new ArrayList();

    public void addText(String text) {
        textList.add(text);
    }

    public List getTextList() {
        return this.textList;
    }
}