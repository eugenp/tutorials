package com.baeldung.interoperability;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArrayExample {

    public int sumValues(int[] nums) {
        int res = 0;

        for (int x:nums) {
            res += x;
        }

        return res;
    }

    public void writeList() throws IOException {
        File file = new File("E://file.txt");
        FileReader fr = new FileReader(file);
        fr.close();
    }
}
