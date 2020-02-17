package com.baeldung.hexagonal.core.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain Class
 * 
 * @author : Udara Gunathilake
 * @email : udara.dhammika@gmail.com
 * @date : Feb 17, 2020
 */
@Getter
@Setter
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
}
