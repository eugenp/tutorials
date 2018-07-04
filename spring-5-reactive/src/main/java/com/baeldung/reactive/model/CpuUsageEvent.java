package com.baeldung.reactive.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CpuUsageEvent {

    private Double cpuUsage;

    private LocalDateTime time;

}
