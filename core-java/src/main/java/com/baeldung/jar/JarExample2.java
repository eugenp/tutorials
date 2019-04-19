package com.baeldung.jar;

public class JarExample2 {

    public static void main(String[] args) {
        System.out.println("Hello Baeldung Reader in JarExample2!");
        
        if(args == null) {
            System.out.println("You have not provided any arguments!");
        }else {
            System.out.println("There are "+args.length+" argument(s)!");
            for(int i=0; i<args.length; i++) {
                System.out.println("Argument("+(i+1)+"):" + args[i]);
            }
        }
    }

}
