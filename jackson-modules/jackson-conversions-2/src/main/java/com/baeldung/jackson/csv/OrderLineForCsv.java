package com.baeldung.jackson.csv;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "count",
    "name"
})
public abstract class OrderLineForCsv {
    
    @JsonProperty("name")
    private String item; 
    
    @JsonProperty("count")
    private int quantity; 
    
    @JsonIgnore
    private BigDecimal unitPrice;
    

}
