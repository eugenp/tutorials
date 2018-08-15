package com.baeldung.java9.process;

import java.util.Scanner;

public class ChildProcess {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Child received: " + input.nextLine());
    }

}
