package com.baeldung.ejbspringcomparison.ejb.stateless;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

@Stateless
public class FinderEJB implements FinderEJBRemote {

    private Map<String, String> alphabet;

    public FinderEJB() {
        alphabet = new HashMap<String, String>();
        alphabet.put("A", "Apple");
        alphabet.put("B", "Ball");
        alphabet.put("C", "Cat");
        alphabet.put("D", "Dog");
    }

    public String search(String keyword) {
        return alphabet.get(keyword);
    }

}
