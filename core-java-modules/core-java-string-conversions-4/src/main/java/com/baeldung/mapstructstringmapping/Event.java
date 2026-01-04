package com.baeldung.mapstructstringmapping;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String name;
    private Date eventDate;
}
