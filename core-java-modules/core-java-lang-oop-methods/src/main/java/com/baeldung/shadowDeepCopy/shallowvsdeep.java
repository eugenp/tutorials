//For shallow Copy part.
//import java.util.ArrayList;
//
//class BaeldungClass {
//    ArrayList<Object> list;
//    public BaeldungClass() {
//        list = new ArrayList<>();
//    }
//}
//
//public class shallowvsdeep{
//    public static void main(String[] args) {
//        BaeldungClass firstObj = new BaeldungClass();
//        firstObj.list.add(20); 
//        firstObj.list.add("Hello"); 
//        firstObj.list.add(10.5f); 
//
//        BaeldungClass secObj = firstObj;
//
//        secObj.list.set(1, "World");
//
//        System.out.println("The value of list in firstObj is: " + firstObj.list);
//        System.out.println("\nThe value of list in secObj is: " + secObj.list);
//    }
//}



//For the  Deep Copy part

package initialProj;

import java.util.ArrayList;

class BaeldungClass {
     ArrayList<Object> list;
     public BaeldungClass() {
        list = new ArrayList<>();
    }
    public BaeldungClass(BaeldungClass original) {
        list = new ArrayList<>(original.list);
    }
}

public class shallowvsdeep {
    public static void main(String[] args) {
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
