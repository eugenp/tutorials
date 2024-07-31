package com.baeldung.customfont;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CustomFonts {
    public static void main(String[] args) {
        usingCustomFonts();
    }

    public static void usingCustomFonts() {
        final GraphicsEnvironment GE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final List<String> AVAILABLE_FONT_FAMILY_NAMES = Arrays.asList(GE.getAvailableFontFamilyNames());
        try {
            final List<File> LIST = Arrays.asList(
                    new File("font/JetBrainsMono/JetBrainsMono-Thin.ttf"),
                    new File("font/JetBrainsMono/JetBrainsMono-Light.ttf"),
                    new File("font/Roboto/Roboto-Light.ttf"),
                    new File("font/Roboto/Roboto-Regular.ttf"),
                    new File("font/Roboto/Roboto-Medium.ttf")
            );
            for (File LIST_ITEM : LIST) {
                if (LIST_ITEM.exists()) {
                    Font FONT = Font.createFont(Font.TRUETYPE_FONT, LIST_ITEM);
                    if (!AVAILABLE_FONT_FAMILY_NAMES.contains(FONT.getFontName())) {
                        GE.registerFont(FONT);
                    }
                }
            }
        } catch (FontFormatException | IOException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }


        JFrame frame = new JFrame("Custom Font Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label1 = new JLabel("TEXT1");
        label1.setFont(new Font("Roboto Medium", Font.PLAIN, 17));

        JLabel label2 = new JLabel("TEXT2");
        label2.setFont(new Font("JetBrainsMono-Thin", Font.PLAIN, 17));

        frame.add(label1);
        frame.add(label2);

        frame.pack();
        frame.setVisible(true);
    }
}