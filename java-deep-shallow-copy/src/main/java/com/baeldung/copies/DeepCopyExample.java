package com.baeldung.copies;

import com.baeldung.models.Flavor;
import com.baeldung.models.Ingredients;
import com.baeldung.models.Smoothie;

public class DeepCopyExample {
    public static void main (String args[]) {
        Flavor flavor = new Flavor("Tofu");
        Ingredients ingredients = new Ingredients("Soymilk");

        Smoothie original = new Smoothie(flavor, ingredients);
        Smoothie deepCopy = new Smoothie(original.getFlavor(), original.getIngredients());
    }
}
