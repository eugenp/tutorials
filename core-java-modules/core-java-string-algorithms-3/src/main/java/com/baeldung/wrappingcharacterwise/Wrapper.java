package com.baeldung.wrappingcharacterwise;

import java.lang.IllegalArgumentException;
import java.lang.String;
import java.lang.StringBuilder;

public class Wrapper {
    
    public String wrapStringCharacterWise(String input, int n) {      
        StringBuilder stringBuilder = new StringBuilder(input);
        int index = 0;
        while(stringBuilder.length() > index + n) {
            int lastLineReturn = stringBuilder.lastIndexOf("\n", index + n);
            if (lastLineReturn > index) {
                index = lastLineReturn;
            } else {
                index = stringBuilder.lastIndexOf(" ", index + n);
                if (index == -1) {
                    throw new IllegalArgumentException("impossible to slice " + stringBuilder.substring(0, n));
                }       
                stringBuilder.replace(index, index + 1, "\n");
                index++;
            }    
        }
        return stringBuilder.toString();
    }

}
