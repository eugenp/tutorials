package com.baeldung.restassured;

import java.io.InputStream;
import java.util.Scanner;

final class Util {

    private Util() {
    }

    static String inputStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
