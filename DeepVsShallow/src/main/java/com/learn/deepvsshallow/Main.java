/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learn.deepvsshallow;

/**
 *
 * @author mantas.pipine
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        //--------------------------------------------------------
        System.out.println("-----------------");
        System.out.println("Constructor copy");

        StudentConstructorExampleObj jane = new StudentConstructorExampleObj(new Name("Jane", "j"), 15);
        StudentConstructorExampleObj kate = new StudentConstructorExampleObj(jane);

        jane.setAge(0);
        jane.name.name = "TEST";
        jane.printInfo();
        kate.printInfo();

        System.out.println("-----------------");
        System.out.println("Clone copy");

        StudentCloneExampleObj janeClone = new StudentCloneExampleObj(new Name("Jane", "j"), 15);
        StudentCloneExampleObj kateClone = janeClone.clone();

 
        janeClone.name.name = "TEST";
        janeClone.printInfo();
        kateClone.printInfo();

    }

}
