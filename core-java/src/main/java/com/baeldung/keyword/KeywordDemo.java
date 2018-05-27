package com.baeldung.keyword;

import com.baeldung.keyword.superkeyword.SuperSub;
import com.baeldung.keyword.thiskeyword.KeywordTest;

/**
 * Created by Gebruiker on 5/14/2018.
 */
public class KeywordDemo {

    public static void main(String[] args) {
        KeywordTest keyword = new KeywordTest();

        SuperSub child = new SuperSub("message from the child class");
    }
}
