package com.baeldung.stringtemplates;

import static java.lang.StringTemplate.RAW;
import static java.util.FormatProcessor.FMT;

public class StringTemplateExamples {
    String interpolationUsingSTRProcessor(String feelsLike, String temperature, String unit) {
        return STR
          . "Today's weather is \{ feelsLike }, with a temperature of \{ temperature } degrees \{ unit }" ;
    }

    String interpolationOfJSONBlock(String feelsLike, String temperature, String unit) {
        return STR
          . """
          {
            "feelsLike": "\{ feelsLike }",
            "temperature": "\{ temperature }",
            "unit": "\{ unit }"
          }
          """ ;
    }

    String interpolationWithExpressions() {
        return STR
          . "Today's weather is \{ getFeelsLike() }, with a temperature of \{ getTemperature() } degrees \{ getUnit() }" ;
    }

    String interpolationWithTemplates() {
        StringTemplate str = RAW
          . "Today's weather is \{ getFeelsLike() }, with a temperature of \{ getTemperature() } degrees \{ getUnit() }" ;
        return STR.process(str);
    }

    String interpolationOfJSONBlockWithFMT(String feelsLike, float temperature, String unit) {
        return FMT
          . """
      {
        "feelsLike": "%1s\{ feelsLike }",
        "temperature": "%2.2f\{ temperature }",
        "unit": "%1s\{ unit }"
      }
      """ ;
    }

    private String getFeelsLike() {
        return "pleasant";
    }

    private String getTemperature() {
        return "25";
    }

    private String getUnit() {
        return "Celsius";
    }
}
