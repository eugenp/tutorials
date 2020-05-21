package com.sumanasaha.hexagonalarchitecturejava.domain;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author ssaha (21.05.20)
 */
@Getter
@Setter
@ToString
public class Student implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private String department;

}
