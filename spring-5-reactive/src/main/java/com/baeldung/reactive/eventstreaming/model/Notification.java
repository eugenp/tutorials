package com.baeldung.reactive.eventstreaming.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Notification {
	
    private String text;
    private LocalTime time;

}