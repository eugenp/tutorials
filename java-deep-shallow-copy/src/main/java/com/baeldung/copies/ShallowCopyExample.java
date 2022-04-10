package com.baeldung.copies;

import com.baeldung.models.Flavor;
import com.baeldung.models.Ingredients;
import com.baeldung.models.Smoothie;

public class ShallowCopyExample {
    public static void main (String args[]) {
        Flavor flavor = new Flavor("Raspberry");
        Ingredients ingredients = new Ingredients("Kefir");

        Smoothie original = new Smoothie(flavor, ingredients);
        Smoothie shallowCopy = original;
    }
}
