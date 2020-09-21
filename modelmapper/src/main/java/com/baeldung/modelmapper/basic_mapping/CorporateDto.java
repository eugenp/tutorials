package com.baeldung.modelmapper.basic_mapping;

import lombok.Data;
import lombok.Value;

@Data
class CorporateDto {
    String headquarterStreet;
    String headquarterCity;
    String managerFirstName;
    String managerLastName;
}
