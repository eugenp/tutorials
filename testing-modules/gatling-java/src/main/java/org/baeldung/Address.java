package org.baeldung;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Address {
    private String postCode;
    private String Street;
    private String houseNo;
    private String city;
}
