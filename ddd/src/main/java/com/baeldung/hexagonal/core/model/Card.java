package com.baeldung.hexagonal.core.model;


public class Card {

    private final String frontSide;
    private final String backSide;

    private Card(final String frontSide, final String backSide) {
        this.frontSide = frontSide;
        this.backSide = backSide;
    }

    private String getFrontSide() {
        return frontSide;
    }

    private String getBackSide() {
        return backSide;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Card card = (Card) o;

        if (frontSide != null ? !frontSide.equals(card.frontSide) : card.frontSide != null) {
            return false;
        }
        return backSide != null ? backSide.equals(card.backSide) : card.backSide == null;
    }

    @Override
    public int hashCode() {
        int result = frontSide != null ? frontSide.hashCode() : 0;
        result = 31 * result + (backSide != null ? backSide.hashCode() : 0);
        return result;
    }
}
