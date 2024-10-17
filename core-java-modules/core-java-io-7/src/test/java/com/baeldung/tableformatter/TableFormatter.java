package com.baeldung.tableformatter;

import de.vandermeer.asciitable.AsciiTable;

public class TableFormatter {

    static void usingStringConcatenation() {
        String[] headers = {"ID", "Name", "Age"};
        String[][] data = { 
            {"1", "James", "24"}, 
            {"2", "Sarah", "27"}, 
            {"3", "Keith", "31"} 
        };

        System.out.println(headers[0] + "   " + headers[1] + "   " + headers[2]);

        for (String[] row : data) {
            System.out.println(row[0] + "   " + row[1] + "   " + row[2]);
        }
    }

    static void usingPrintf() {
        String[] headers = {"ID", "Name", "Age"};
        String[][] data = { 
            {"1", "James", "24"}, 
            {"2", "Sarah", "27"}, 
            {"3", "Keith", "31"} 
        };

        System.out.printf("%-5s %-10s %-5s%n", headers[0], headers[1], headers[2]);

        for (String[] row : data) {
            System.out.printf("%-5s %-10s %-5s%n", row[0], row[1], row[2]);
        }
    }

    static void usingStringFormat() {
        String[] headers = {"ID", "Name", "Age"};
        String[][] data = { 
            {"1", "James", "24"}, 
            {"2", "Sarah", "27"}, 
            {"3", "Keith", "31"} 
        };

        String header = String.format("%-5s %-10s %-5s", headers[0], headers[1], headers[2]);
        System.out.println(header);

        for (String[] row : data) {
            String formattedRow = String.format("%-5s %-10s %-5s", row[0], row[1], row[2]);
            System.out.println(formattedRow);
        }
    }
    
    static void usingStringBuilder() {
        String[] headers = {"ID", "Name", "Age"};
        String[][] data = { 
            {"1", "James", "24"}, 
            {"2", "Sarah", "27"}, 
            {"3", "Keith", "31"} 
        };

        StringBuilder table = new StringBuilder();

        table.append(String.format("%-5s %-10s %-5s%n", headers[0], headers[1], headers[2]));


        for (String[] row : data) {
            table.append(String.format("%-5s %-10s %-5s%n", row[0], row[1], row[2]));
        }

        System.out.print(table.toString());
    }

    static void usingAsciiTable() { 
        AsciiTable table = new AsciiTable(); 
        table.addRule(); 
        table.addRow("ID", "Name", "Age"); 
        table.addRule(); 
        table.addRow("1", "James", "24"); 
        table.addRow("2", "Sarah", "27"); 
        table.addRow("3", "Keith", "31"); 
        table.addRule(); 
        
        String renderedTable = table.render(); 
        System.out.println(renderedTable); 
    }
}
