package com.baeldung.shallowcopyvsdeepcopy;

import java.util.*;

public class ShallowCopy { 
    public static void main(String[] args) throws CloneNotSupportedException { 
        shallowCopyExample(); 

        
    }
   
    public static void shallowCopyExample() throws CloneNotSupportedException { 
        System.out.println("ShallowCopy Example"); 
        System.out.println("----------------------------------------------"); 
        Fruit myFruit = new Fruit(7, new String[]{"Apple", "Watermelon", "Orange", "Pear", "Cherry", "Strawberry", "Grape"}, "simple fruit"); 
        Fruit cloneOfMyFruit = (Fruit)myFruit.clone(); 
        System.out.println("Original Fruit: "+ myFruit); 
        System.out.println("ShallowCopy/Clone of Fruit: "+ cloneOfMyFruit); 
        
        System.out.println("Modifying Original Fruit"); 
        myFruit.typeOfFruit = "Fleshy Fruit"; 
        myFruit.numberOfFruits = 10; 
        myFruit.fruits[4] = "Lemon"; 
        System.out.println("Original myFruit after modification: "+ myFruit); 
        System.out.println("ShallowCopy of myFruit after modification: "+ cloneOfMyFruit);
    } 
  
    
    public static class Fruit implements Cloneable{ 
        public long numberOfFruits; //primitive 
        public String[] fruits; //reference type 
        public String typeOfFruit; //immutable 
        
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
       
        
        @Override public String toString() { 
            return "Fruit{" +
            "numberOfFruits=" + numberOfFruits + 
            ", fruits=" + Arrays.toString(fruits) + 
            ", typeOfFruit='" + typeOfFruit + '\'' + '}'; 
            
        } 
        
    } 
    
}

