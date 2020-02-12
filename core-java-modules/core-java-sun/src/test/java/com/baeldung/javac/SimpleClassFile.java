package com.baeldung.javac;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/** Holds compiled byte code in a byte array */
public class SimpleClassFile extends SimpleJavaFileObject {

    private ByteArrayOutputStream out;

    public SimpleClassFile(URI uri) {
        super(uri, Kind.CLASS);
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return out = new ByteArrayOutputStream();
    }

    public byte[] getCompiledBinaries() {
        return out.toByteArray();
    }
}