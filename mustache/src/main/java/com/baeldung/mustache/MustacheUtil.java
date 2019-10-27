package com.baeldung.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

public class MustacheUtil {

    private static final MustacheFactory mf = new DefaultMustacheFactory();
    
    public static MustacheFactory getMustacheFactory(){
        return mf;
    }
    
    
}
