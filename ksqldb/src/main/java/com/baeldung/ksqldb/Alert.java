package com.baeldung.ksqldb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Alert {

    @JsonProperty(value = "SENSOR_ID")
    private String sensorId;

    @JsonProperty(value = "START_PERIOD")
    private String startPeriod;

    @JsonProperty(value = "END_PERIOD")
    private String endPeriod;

    @JsonProperty(value = "AVERAGE_READING")
    private double averageReading;

}
