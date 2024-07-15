//import java.util.ArrayList;
//
//class BaeldungClass {
//    // instance variable of the class ABC
//    ArrayList<Object> list;
//
//    // constructor to initialize the ArrayList
//    public BaeldungClass() {
//        list = new ArrayList<>();
//    }
//}
//
//public class shallowvsdeep{
//    public static void main(String[] args) {
//        // creating an object of the class ABC
//        BaeldungClass firstObj = new BaeldungClass();
//        firstObj.list.add(20);       // adding an integer
//        firstObj.list.add("Hello");  // adding a string
//        firstObj.list.add(10.5f);    // adding a float
//
//        // it will copy the reference, not value
//        BaeldungClass secObj = firstObj;
//
//        // updating the second element value to "World" using the reference variable secObj
//        secObj.list.set(1, "World");
//
//        // printing the value of list using reference variable firstObj
//        System.out.println("The value of list in firstObj is: " + firstObj.list);
//
//        // printing the value of list using reference variable secObj
//        System.out.println("\nThe value of list in secObj is: " + secObj.list);
//    }
//}




package initialProj;

import java.util.ArrayList;

class BaeldungClass {
    // Instance variable of the class BaeldungClass
    ArrayList<Object> list;

    // Constructor to initialize the ArrayList
    public BaeldungClass() {
        list = new ArrayList<>();
    }

    // Constructor to create a deep copy
    public BaeldungClass(BaeldungClass original) {
        list = new ArrayList<>(original.list);
    }
}

public class shallowvsdeep {
    public static void main(String[] args) {
        // Creating an object of the class BaeldungClass
        BaeldungClass firstObj = new BaeldungClass();
        firstObj.list.add(20);       // Adding an integer
        firstObj.list.add("Hello");  // Adding a string
        firstObj.list.add(10.5f);    // Adding a float

        // Creating a deep copy of the object
        BaeldungClass secObj = new BaeldungClass(firstObj);

        // Updating the second element value to "World" using the reference variable secObj
        secObj.list.set(1, "World");

        // Printing the value of list using reference variable firstObj
        System.out.println("The value of list in firstObj is: " + firstObj.list);

        // Printing the value of list using reference variable secObj
        System.out.println("\nThe value of list in secObj is: " + secObj.list);
    }
}
