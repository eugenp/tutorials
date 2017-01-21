package com.java.test;

/**
 * Created by s738204 on 11/01/2017.
 */
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
public class FileUtility {
    public static void serializeGivenObject(String filePath , Object object ) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch(Exception e) {
            System.out.println("Exception in serializeGivenObject "+e.getMessage());
            e.printStackTrace();
        }
    }

    public static Object deserializeTheObjectBack(String filePath) {
        Object deserializedObject = null;
        try {
            FileInputStream fileIn =
                    new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            deserializedObject = in.readObject();

            in.close();
            fileIn.close();
        } catch(Exception e) {
            System.out.println("Exception in deserializeTheObjectBack "+e.getMessage());
        }
        return deserializedObject;
    }

    public static void main(String ar[]) {
        Emp emp=new Emp();
        emp.setId(123);
        emp.setName("Derek");
        FileUtility.serializeGivenObject("C:\\Users\\S738204\\EGSWORKSPACE\\TestCases\\src\\test\\java\\com\\ek\\test\\steps\\emp.ser",emp);

        Emp empDeserialized = (Emp) FileUtility.deserializeTheObjectBack("C:\\Users\\S738204\\EGSWORKSPACE\\TestCases\\src\\test\\java\\com\\ek\\test\\steps\\emp.ser");
        System.out.println("Id , Name : " + empDeserialized.getId()+" , "+empDeserialized.getName());
    }
}



