package com.baeldung.folding;

/**
 * Code snippet for article "A Guide to the Folding Technique".
 *  
 * @author A.Shcherbakov
 *
 */
public class Main {

    public static void main(String... arg) {
        FoldingHash hasher = new FoldingHash();
        final String str = "Java language";
        System.out.println(hasher.hash(str, 2, 100_000));
        System.out.println(hasher.hash(str, 3, 1_000));
    }
}
