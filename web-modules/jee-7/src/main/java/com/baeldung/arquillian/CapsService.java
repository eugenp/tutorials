package com.baeldung.arquillian;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class CapsService {
    @Inject
    private CapsConvertor capsConvertor;
    
    public String getConvertedCaps(final String word){
        return capsConvertor.getLowerCase().convert(word);
    }
}