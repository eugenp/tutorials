package com.baeldung.lombok.intro;

import java.util.ArrayList;

import lombok.val;

public class TypeInferred {

    public String lombokTypeInferred() {

        val list = new ArrayList<String>();
        list.add("Hello, Lombok!");
        val listElem = list.get(0);
        return listElem.toLowerCase();
    }

}
