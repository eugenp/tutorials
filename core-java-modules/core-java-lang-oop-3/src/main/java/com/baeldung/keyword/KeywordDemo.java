package com.baeldung.keyword;

import com.baeldung.keyword.superkeyword.SuperSub;
import com.baeldung.keyword.thiskeyword.KeywordUnitTest;

/**
 * Created by Gebruiker on 5/14/2018.
 */
public class KeywordDemo {

    public static void main(String[] args) {
        KeywordUnitTest keyword = new KeywordUnitTest();

        SuperSub child = new SuperSub("message from the child class");
    }
}
