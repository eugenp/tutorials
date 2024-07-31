package org.example;

record StudentRecordV2(String name, int rollNo, int marks, char grade) {
    public StudentRecordV2(String name, int rollNo, int marks) {
        this(name, rollNo, marks, calculateGrade(marks));
    }

    private static char calculateGrade(int marks) {
        if (marks >= 90) {
            return 'A';
        } else if (marks >= 80) {
            return 'B';
        } else if (marks >= 70) {
            return 'C';
        } else if (marks >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}

