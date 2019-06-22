package com.baeldung.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "CALCULATIONS")
@AllArgsConstructor
@Getter
@Builder
public class Calculations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer number1;
    private Integer number2;
    private Integer sum;
}
