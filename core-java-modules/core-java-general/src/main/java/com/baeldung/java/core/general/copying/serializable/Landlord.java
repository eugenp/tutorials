package com.baeldung.java.core.general.copying.serializable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Landlord implements Serializable {
    private static final long serialVersionUID = 2305586867457443976L;

    private String name;
    private String surname;
}
