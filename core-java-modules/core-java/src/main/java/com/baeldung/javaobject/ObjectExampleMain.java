package com.baeldung.javaobject;

        public class ObjectExampleMain {
            public static void main(String args[]){

                ObjectExample example= new ObjectExample();
                //Creating a new reference and a new instance of the class Object Example
                ObjectExample deepCopyExample=new ObjectExample();
                //Changing the value of name
                deepCopyExample.name="Core Java";

                //The output is "Name: Java"
                System.out.println("Name: "+example.name);
                //The output is "Name: Core Java"
                System.out.println("Name: "+deepCopyExample.name);



                ObjectExample example2= new ObjectExample();
                //Creating a new object with the same reference
                ObjectExample shallowCopyExample=example2;
                shallowCopyExample.name="Core Java";
                //Output is "Name: Core Java"
                System.out.println("Name: "+example2.name);
                //Output is "Name: Core Java"
                System.out.println("Name: "+shallowCopyExample.name);
            }
        }

