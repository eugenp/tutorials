package com.baeldung.java21;

public class RecordPattern {

    record Point(int x, int y) {}
    
    public static int beforeRecordPattern(Object obj) {
        int sum = 0;
        if(obj instanceof Point p) {
            int x = p.x();
            int y = p.y();
            sum = x+y;
        }
        return sum;
    }
    
    public static int afterRecordPattern(Object obj) {
        if(obj instanceof Point(int x, int y)) {
           return x+y;
        }
        return 0;
    }
    
    enum Color {RED, GREEN, BLUE}
    
    record ColoredPoint(Point point, Color color) {}
    
    record RandomPoint(ColoredPoint cp) {}
    
    public static Color getRamdomPointColor(RandomPoint r) {
        if(r instanceof RandomPoint(ColoredPoint cp)) {
            return cp.color();
        }
        return null;
    }
}
