package com.baeldung.batchreaderproperties.model;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Medicine {
    private UUID id;
    private String name;
    private MedicineCategory type;
    private Timestamp expirationDate;
    private Double originalPrice;
    private Double salePrice;
}
