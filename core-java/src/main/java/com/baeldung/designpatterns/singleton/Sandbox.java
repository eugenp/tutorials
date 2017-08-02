package com.baeldung.designpatterns.singleton;

public class Sandbox {

    public static void main(String[] args) {
        
        OurSingleton object1 = OurSingleton.getInstance();
        //OurSingleton object1 = new OurSingleton(); //Unresolved compilation problem: The constructor OurSingleton() is not visible
        
        System.out.println(object1.getInfo()); //Initial info
        
        OurSingleton object2 = OurSingleton.getInstance();
        object2.setInfo("New info");
        
        System.out.println(object1.getInfo()); //New info
        System.out.println(object2.getInfo()); //New info
    }
}
