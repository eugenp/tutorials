package com.baeldung.java.core.general.copying.serializable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Building implements Serializable {
    private static final long serialVersionUID = -1750138394920756559L;

    private String address;
    private String status;
    private Landlord landlord;
}
