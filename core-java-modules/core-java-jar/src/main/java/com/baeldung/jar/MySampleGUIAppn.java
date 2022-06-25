package com.baeldung.jar;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class MySampleGUIAppn extends JFrame{
    public  MySampleGUIAppn() {
        setSize(300,300);
        setTitle("MySampleGUIAppn");
        Button b = new Button("Click Me!");
        b.setBounds(30,100,80,30);
        add(b);
        if (!GraphicsEnvironment.isHeadless()) {
            setVisible(true);
        }
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        MySampleGUIAppn app=new  MySampleGUIAppn();
    }
}
