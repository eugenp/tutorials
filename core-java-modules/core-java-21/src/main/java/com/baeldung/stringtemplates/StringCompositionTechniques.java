package com.baeldung.stringtemplates;

import java.text.MessageFormat;

public class StringCompositionTechniques {
    String composeUsingPlus(String feelsLike, String temperature, String unit) {
        return "Today's weather is " + feelsLike + ", with a temperature of " + temperature + " degrees " + unit;
    }

    String composeUsingStringBuffer(String feelsLike, String temperature, String unit) {
        return new StringBuffer().append("Today's weather is ")
          .append(feelsLike)
          .append(", with a temperature of ")
          .append(temperature)
          .append(" degrees ")
          .append(unit)
          .toString();
    }

    String composeUsingStringBuilder(String feelsLike, String temperature, String unit) {
        return new StringBuilder().append("Today's weather is ")
          .append(feelsLike)
          .append(", with a temperature of ")
          .append(temperature)
          .append(" degrees ")
          .append(unit)
          .toString();
    }

    String composeUsingFormatters(String feelsLike, String temperature, String unit) {
        return String.format("Today's weather is %s, with a temperature of %s degrees %s", feelsLike, temperature, unit);
    }

    String composeUsingMessageFormatter(String feelsLike, String temperature, String unit) {
        return MessageFormat.format("Today''s weather is {0}, with a temperature of {1} degrees {2}", feelsLike, temperature, unit);
    }
}
