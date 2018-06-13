package com.baeldung.webflux.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherEvent {

    private Weather weather;

    private LocalDateTime date;

}
