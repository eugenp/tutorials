package com.baeldung;

public class ShallowDeepCopyApplication {
    public static void main(String[] args) {
        // Shallow Copy
        Profession professionA = new Profession("salesMan", 60000.0);
        Profession professionB = professionA; // Shallow copy
        professionB.setJobTitle("storeManager");
        System.out.println(professionA.getJobTitle().equals(professionB.getJobTitle())); // True

        //Deep Copy
        Profession professionY = new Profession("Airplane Pilot", 150000.00);
        Profession professionZ = new Profession(professionY.getJobTitle(), professionY.getSalary()); // Deep copy
        professionZ.setJobTitle("IT Manager");
        System.out.println(professionZ.getJobTitle().equals(professionY.getJobTitle())); // False

    }


}
