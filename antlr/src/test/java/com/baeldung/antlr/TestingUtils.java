package com.baeldung.antlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import java.io.InputStream;

public class TestingUtils {

    public static CharStream loadResourceAsCharStream(String resourceFileName) throws Exception{
        InputStream is = TestingUtils.class.getClassLoader().getResourceAsStream(resourceFileName);
        return CharStreams.fromStream(is);
    }
}
