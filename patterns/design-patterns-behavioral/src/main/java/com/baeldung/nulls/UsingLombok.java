package com.baeldung.nulls;

import lombok.NonNull;

public class UsingLombok {

    public void accept(@NonNull Object param){
        System.out.println(param);
    }
}
