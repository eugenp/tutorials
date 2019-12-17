package com.baeldung.dddhexagonalspringboot.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrder {

    private Long id;
    private LocalDateTime date;

}
