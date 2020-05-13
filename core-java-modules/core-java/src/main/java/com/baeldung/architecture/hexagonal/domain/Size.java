package com.baeldung.architecture.hexagonal.domain;

public enum Size {
    TWELVE, SIXTEEN, TWENTY, TWENTYFOUR, FOURTYSIX, FIFTY, FIFTYTWO, FIFTYFOUR;

    boolean isAdultBike() {
        switch (this) {
        case TWELVE:
        case SIXTEEN:
        case TWENTY:
        case TWENTYFOUR:
            return false;
        default:
            return true;
        }
    }
}
