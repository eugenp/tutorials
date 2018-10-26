package com.baeldung.hexagonaldesign.impl;

import com.baeldung.hexagonaldesign.UserInput;

import java.util.Scanner;

public class KeyboardInput implements UserInput {
    @Override
    public int input() {
        Scanner keyboard = new Scanner(System.in);
        return keyboard.nextInt();
    }
}
