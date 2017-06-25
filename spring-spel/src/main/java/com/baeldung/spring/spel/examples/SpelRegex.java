package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelRegex")
public class SpelRegex {

    @Value("#{100 matches '\\d+' }")
    private boolean validNumericStringResult;

    @Value("#{'100fghdjf' matches '\\d+' }")
    private boolean invalidNumericStringResult;

    @Value("#{'valid alphabetic string' matches '[a-zA-Z\\s]+' }")
    private boolean validAlphabeticStringResult;

    @Value("#{'invalid alphabetic string #$1' matches '[a-zA-Z\\s]+' }")
    private boolean invalidAlphabeticStringResult;

    @Value("#{engine.horsePower matches '\\d+'}")
    private boolean validFormatOfHorsePower;

    public boolean isValidNumericStringResult() {
        return validNumericStringResult;
    }

    public void setValidNumericStringResult(boolean validNumericStringResult) {
        this.validNumericStringResult = validNumericStringResult;
    }

    public boolean isInvalidNumericStringResult() {
        return invalidNumericStringResult;
    }

    public void setInvalidNumericStringResult(boolean invalidNumericStringResult) {
        this.invalidNumericStringResult = invalidNumericStringResult;
    }

    public boolean isValidAlphabeticStringResult() {
        return validAlphabeticStringResult;
    }

    public void setValidAlphabeticStringResult(boolean validAlphabeticStringResult) {
        this.validAlphabeticStringResult = validAlphabeticStringResult;
    }

    public boolean isInvalidAlphabeticStringResult() {
        return invalidAlphabeticStringResult;
    }

    public void setInvalidAlphabeticStringResult(boolean invalidAlphabeticStringResult) {
        this.invalidAlphabeticStringResult = invalidAlphabeticStringResult;
    }

    public boolean isValidFormatOfHorsePower() {
        return validFormatOfHorsePower;
    }

    public void setValidFormatOfHorsePower(boolean validFormatOfHorsePower) {
        this.validFormatOfHorsePower = validFormatOfHorsePower;
    }

    @Override
    public String toString() {
        return "SpelRegex{" +
                "validNumericStringResult=" + validNumericStringResult +
                ", invalidNumericStringResult=" + invalidNumericStringResult +
                ", validAlphabeticStringResult=" + validAlphabeticStringResult +
                ", invalidAlphabeticStringResult=" + invalidAlphabeticStringResult +
                ", validFormatOfHorsePower=" + validFormatOfHorsePower +
                '}';
    }
}
