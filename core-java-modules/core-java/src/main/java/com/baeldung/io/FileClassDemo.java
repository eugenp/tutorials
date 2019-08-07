package com.baeldung.io;

import java.io.File;
import java.net.URI;

public class FileClassDemo {

    public static File withPathNameOnly(String pathname) {
        return new File(pathname);
    }

    public static File withParent(File parent, String child) {
        return new File(parent, child);
    }

    public static File withParent(String parent, String child) {
        return new File(parent, child);
    }

    public static File withURI(URI uri) {
        return new File(uri);
    }

}
