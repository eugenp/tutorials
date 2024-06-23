package main;

import org.example.DeepNotebook;
import org.example.ShallowNotebook;

public class DeepMain {

    public static void main(String s[]){

        DeepNotebook original = new DeepNotebook();
        original.addRecipe("Chocolate Chip Cookies", "Easy to bake.");

        DeepNotebook copy = new DeepNotebook(original);

        System.out.println("Original Object "+original.recipes.get(0));
        System.out.println("Copied Object" + copy.recipes.get(0));  // Same Recipe object

    }
}
