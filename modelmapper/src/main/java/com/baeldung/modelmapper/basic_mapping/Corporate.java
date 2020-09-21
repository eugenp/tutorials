package com.baeldung.modelmapper.basic_mapping;

import lombok.Value;

@Value
class Corporate {
    Address headquarterAddress;
    Manager manager;
}
