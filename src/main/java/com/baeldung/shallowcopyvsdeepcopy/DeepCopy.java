package com.baeldung.shallowcopyvsdeepcopy;

import java.util.*;

public class DeepCopy { 
    public static void main(String[] args) throws CloneNotSupportedException { 
        
        deepCopyExample(); 
        
    }
   
    
    public static void deepCopyExample() { 
        System.out.println("DeepCopy Example"); 
        System.out.println("----------------------------------------------"); 
        Fruit fruit = new Fruit(7, new String[]{"Apple", "Watermelon", "Orange", "Pear", "Cherry", "Strawberry", "Grape"}, "simple fruit"); 
        Fruit deepcopiedFruit = fruit.deepCopy(); 
        System.out.println("Original Fruit: "+ fruit); 
        System.out.println("Deep Copy of Fruit: "+ deepcopiedFruit); 
        
        System.out.println("Modifying Original Fruit"); 
        fruit.typeOfFruit = "Fleshy Fruit"; 
        fruit.numberOfFruits = 10; 
        fruit.fruits[4] = "Lemon"; 
        System.out.println("Original Fruit after modification: "+ fruit); 
        System.out.println("DeepCopy of fruit after modification: "+ deepcopiedFruit);
    } 
    
    public static class Fruit implements Cloneable{ 
        public long numberOfFruits;
        public String[] fruits; 
        public String typeOfFruit; 
        
        public Fruit(final long numberOfFruits, final String[] fruits, final String typeOfFruit) { 
            
            this.numberOfFruits = numberOfFruits; 
            this.fruits = fruits; 
            this.typeOfFruit = typeOfFruit; 
            
        } 
        
     
        public Fruit(Fruit fruit) { 
            this.fruits = fruit.fruits; 
            this.numberOfFruits = fruit.numberOfFruits; 
            this.typeOfFruit = fruit.typeOfFruit; 
            
        } 
        
        @Override protected Object clone() throws CloneNotSupportedException { 
            return super.clone(); 
            
        } 

        
        public Fruit deepCopy() {
            
            long numberCopied = this.numberOfFruits; 
            String typeOfFruitCopied = this.typeOfFruit; 
            String[] copiedFruit = new String[this.fruits.length]; 
            String[] fruitToCopy = this.fruits; 
            
            for (int i = 0; i < fruitToCopy.length; i++) { 
                copiedFruit[i] = fruitToCopy[i]; 
                
            } 
            
            return new Fruit(numberCopied, copiedFruit, typeOfFruitCopied); 
            
        } 
        
        @Override public String toString() { 
            return "Fruit{" +
            "numberOfFruits=" + numberOfFruits + 
            ", fruits=" + Arrays.toString(fruits) + 
            ", typeOfFruit='" + typeOfFruit + '\'' + '}'; 
            
        } 
        
    } 
    
}

