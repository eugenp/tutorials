package com.baeldung.switchstatement;

public class SwitchStatement {
    // Method 1: Assign grade using if-else
    public String assignGradeUsingIfElse(int score) {
        if (score >= 90) {
            return "Grade: A";
        } else if (score >= 80) {
            return "Grade: B";
        } else if (score >= 70) {
            return "Grade: C";
        } else if (score >= 60) {
            return "Grade: D";
        }
        return "Grade: F";
    }

    // Method 2: Assign grade using switch with integer division
    public String assignGradeUsingRangesWithIntegerDivision(int score) {
        int range = score / 10;

        switch (range) {
            case 10:
            case 9:
                return "Grade: A";
            case 8:
                return "Grade: B";
            case 7:
                return "Grade: C";
            case 6:
                return "Grade: D";
            default:
                return "Grade: F";
        }
    }

    // Method 3: Assign grade using enums
    enum ScoreRange {
        A, B, C, D, F
    }

    public String assignGradeUsingEnum(int score) {
        ScoreRange range = ScoreRange.F; // Default assignment

        if (score >= 90) {
            range = ScoreRange.A;
        } else if (score >= 80) {
            range = ScoreRange.B;
        } else if (score >= 70) {
            range = ScoreRange.C;
        } else if (score >= 60) {
            range = ScoreRange.D;
        }

        return "Grade: " + range;
    }

}
