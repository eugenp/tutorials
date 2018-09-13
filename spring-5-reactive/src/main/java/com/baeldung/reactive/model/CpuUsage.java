package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CpuUsage {
    
    int usage;
    
    String severity;

}
