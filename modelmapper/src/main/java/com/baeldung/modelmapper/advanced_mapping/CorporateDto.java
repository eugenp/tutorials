package com.baeldung.modelmapper.advanced_mapping;

import lombok.Data;

@Data
class CorporateDto {
    String headquarterStreet;
    String headquarterCity;
    String managerFirstName;
    String managerLastName;
    String foundedIn;
    String noOfStreet;
}
