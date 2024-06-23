package main;

import org.example.ShallowNotebook;

public class ShallowMain {

    public static void main(String s[]){

        ShallowNotebook original = new ShallowNotebook();
        original.addRecipe("Pasta Carbonara", "Classic Italian pasta.");

        ShallowNotebook copy = new ShallowNotebook(original);

        System.out.println("Original Object "+original.recipes.get(0));
        System.out.println("Copied Object" + copy.recipes.get(0));  // Same Recipe object

    }

}
