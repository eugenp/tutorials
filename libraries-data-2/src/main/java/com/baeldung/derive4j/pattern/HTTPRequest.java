package com.baeldung.derive4j.pattern;

import org.derive4j.Data;

@Data
interface HTTPRequest {
    interface Cases<R>{
        R GET(String path);
        R POST(String path, String body);
        R PUT(String path, String body);
        R DELETE(String path);
    }

     <R> R match(Cases<R> method);
}
