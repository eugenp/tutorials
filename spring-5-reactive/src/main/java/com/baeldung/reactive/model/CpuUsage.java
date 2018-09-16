package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class CpuUsage {
    
    int usage;
    
    String severity;

}
