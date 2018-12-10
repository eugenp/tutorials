package com.baeldung.derive4j.lazy;

import org.derive4j.Data;
import org.derive4j.Derive;
import org.derive4j.Make;

@Data(value = @Derive(
    inClass = "{ClassName}Impl",
    make = {Make.lazyConstructor, Make.constructors}
))
public interface LazyRequest {
    interface Cases<R>{
        R GET(String path);
        R POST(String path, String body);
        R PUT(String path, String body);
        R DELETE(String path);
    }

    <R> R match(LazyRequest.Cases<R> method);
}

