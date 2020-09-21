package com.baeldung.modelmapper.advanced_mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Corporate {
    Address headquarterAddress;
    Manager manager;
    int foundationYear;
}
