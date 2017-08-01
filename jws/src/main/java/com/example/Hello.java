package com.example;

import javax.swing.*;

public class Hello {
    public static void main(String[] args) {
        JFrame f = new JFrame("main");
        f.setSize(200, 100);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Hello, world!");
        f.add(label);
        f.setVisible(true);
    }
}
