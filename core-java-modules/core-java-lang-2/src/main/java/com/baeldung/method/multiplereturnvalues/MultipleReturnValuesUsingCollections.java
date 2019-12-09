package com.baeldung.method.multiplereturnvalues;

import java.util.HashMap;
import java.util.Map;

class MultipleReturnValuesUsingCollections {

    Map<String, Object> getStudentData(){
        
        Map<String, Object> map = new HashMap<>();
        
        map.put("name", "Alex");
        map.put("age", 15);
        
        return map;
    }
    
}
