package app;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

interface AdderReaderPort {
    int read();
}

class StdioReader implements AdderReaderPort {
    Scanner in = new Scanner(System.in);
 
    public int read() {
        return in.nextInt();
    }
}
 
class MockReader implements AdderReaderPort {
    List<Integer> myNumbers = Arrays.asList(1,2,3,4);
    int next = 0;

    public int read() {
        if (next >= myNumbers.size()) return 0;

        return myNumbers.get(next ++);
    }
}

public class Adder {
 
    public static void main(String[] args) {
        AdderReaderPort reader = new MockReader();
        int total = 0, num = 0;
 
        do {
            num = reader.read();
            total += num;
        } while(num != 0);
 
 
        System.out.println(total);
    }
}