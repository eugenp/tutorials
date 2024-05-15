package com.baeldung.cloud.openfeign.defaulterrorhandling.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FormData {

    int id;
    String name;
}
