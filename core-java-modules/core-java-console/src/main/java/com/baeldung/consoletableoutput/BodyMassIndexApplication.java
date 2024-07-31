package com.baeldung.consoletableoutput;

import java.util.ArrayList;
import java.util.List;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class BodyMassIndexApplication {

    public static void main(String[] args) {
        stringFormat();
        asciiTable();

    }

    public static void stringFormat() {
        List<BodyMassIndex> bodyMassIndices = new ArrayList<>();
        bodyMassIndices.add(new BodyMassIndex("Tom", 1.8, 80));
        bodyMassIndices.add(new BodyMassIndex("Elton", 1.9, 90));
        bodyMassIndices.add(new BodyMassIndex("Harry", 1.9, 90));
        bodyMassIndices.add(new BodyMassIndex("Hannah", 1.9, 90));

        String leftAlignment = "| %-7s | %-7.2f | %-7.2f | %-5.2f |%n";

        System.out.format("+---------+---------+---------+-------+%n");
        System.out.format("| Name    | Height  |  Weight | BMI   |%n");
        System.out.format("+---------+---------+---------+-------+%n");

        for (BodyMassIndex bodyMassIndex : bodyMassIndices) {
            System.out.format(leftAlignment, bodyMassIndex.getName(), bodyMassIndex.getHeight(), bodyMassIndex.getWeight(), bodyMassIndex.calculate());
            System.out.format("+---------+---------+---------+-------+%n");
        }

    }

    public static void asciiTable() {
        List<BodyMassIndex> bodyMassIndices = new ArrayList<>();
        bodyMassIndices.add(new BodyMassIndex("Tom", 1.8, 80));
        bodyMassIndices.add(new BodyMassIndex("Elton", 1.9, 90));
        bodyMassIndices.add(new BodyMassIndex("Harry", 1.9, 90));
        bodyMassIndices.add(new BodyMassIndex("Hannah", 1.9, 90));

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Name", "Height", "Weight", "BMI");
        asciiTable.addRule();

        for (BodyMassIndex bodyMassIndex : bodyMassIndices) {

            asciiTable.addRow(bodyMassIndex.getName(), bodyMassIndex.getHeight(), bodyMassIndex.getWeight(), bodyMassIndex.calculate());
            asciiTable.addRule();

        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);

    }

}
