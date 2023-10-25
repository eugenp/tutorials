package com.baeldung.snippettag;

/**
 * 
 * Using the replace tag
 * {@snippet :
 * public void helloBaeldung() {
 *     System.out.println("Hello From Team Baeldung"); // @replace regex='".*"' replacement="..."
 * }
 * }
 * Using the link tag
 * {@snippet :
 * public void helloBaeldung() {
 *     System.out.println("Hello From Team Baeldung"); // @link substring="System.out" target="System#out"
 * }
 * }
 *
 */

public class GreetingsReplaceAndLinkTag {
    public void helloBaeldung() {
        System.out.println("Hello From Team Baeldung");
    }
}
