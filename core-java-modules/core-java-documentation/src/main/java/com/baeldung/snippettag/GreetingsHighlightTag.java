package com.baeldung.snippettag;


/**
 * The code below shows a full highlighted line
 * {@snippet :
 * public void helloBaeldung() {
 *     System.out.println("Hello From Team Baeldung"); // @highlight
 * }
 * }
 * 
 * highlighting a substring
 * {@snippet :
 * public void helloBaeldung() {
 *     System.out.println("Hello From Team Baeldung"); // @highlight substring="println"
 * }
 * }
 * 
 * highlighting texts on multiple lines
 * {@snippet : 
 * public void helloBaeldung() { 
 *     System.out.println("Hello From Team Baeldung"); // @highlight region substring="println"
 *     String country = "USA"; 
 *     System.out.println("Hello From Team " + country); // @end 
 * } 
 * }
 * 
 */

public class GreetingsHighlightTag {
    public void helloBaeldung() {
        System.out.println("Hello From Team Baeldung");
    }

}
