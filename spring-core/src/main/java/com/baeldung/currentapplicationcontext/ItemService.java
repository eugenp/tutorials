package com.baeldung.currentapplicationcontext;

import org.springframework.stereotype.Service;

@Service
public class ItemService {

    public String getItem(){
        return "New Item";
    }
}
