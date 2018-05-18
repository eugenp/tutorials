package com.baeldung.collection;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Gebruiker on 5/18/2018.
 */
public class CollectionsBean {

    @Autowired
    private List<String> nameList;

    public void printNameList() {
        System.out.println(nameList);
    }
}
