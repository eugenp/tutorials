package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelRelational")
public class SpelRelational {
    @Value("#{1 == 1}")
    private boolean equal;

    @Value("#{1 eq 1}")
    private boolean equalAlphabetic;

    @Value("#{1 != 1}")
    private boolean notEqual;

    @Value("#{1 ne 1}")
    private boolean notEqualAlphabetic;

    @Value("#{1 < 1}")
    private boolean lessThan;

    @Value("#{1 lt 1}")
    private boolean lessThanAlphabetic;

    @Value("#{1 <= 1}")
    private boolean lessThanOrEqual;

    @Value("#{1 le 1}")
    private boolean lessThanOrEqualAlphabetic;

    @Value("#{1 > 1}")
    private boolean greaterThan;

    @Value("#{1 gt 1}")
    private boolean greaterThanAlphabetic;

    @Value("#{9 >= 6}")
    private boolean greaterThanOrEqual;

    @Value("#{9 ge 6}")
    private boolean greaterThanOrEqualAlphabetic;

    @Value("#{250 > 200 && 200 < 4000}")
    private boolean and;

    @Value("#{250 > 200 and 200 < 4000}")
    private boolean andAlphabetic;

    @Value("#{400 > 300 || 150 < 100}")
    private boolean or;

    @Value("#{400 > 300 or 150 < 100}")
    private boolean orAlphabetic;

    @Value("#{!true}")
    private boolean not;

    @Value("#{not true}")
    private boolean notAlphabetic;

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public boolean isEqualAlphabetic() {
        return equalAlphabetic;
    }

    public void setEqualAlphabetic(boolean equalAlphabetic) {
        this.equalAlphabetic = equalAlphabetic;
    }

    public boolean isNotEqual() {
        return notEqual;
    }

    public void setNotEqual(boolean notEqual) {
        this.notEqual = notEqual;
    }

    public boolean isNotEqualAlphabetic() {
        return notEqualAlphabetic;
    }

    public void setNotEqualAlphabetic(boolean notEqualAlphabetic) {
        this.notEqualAlphabetic = notEqualAlphabetic;
    }

    public boolean isLessThan() {
        return lessThan;
    }

    public void setLessThan(boolean lessThan) {
        this.lessThan = lessThan;
    }

    public boolean isLessThanAlphabetic() {
        return lessThanAlphabetic;
    }

    public void setLessThanAlphabetic(boolean lessThanAlphabetic) {
        this.lessThanAlphabetic = lessThanAlphabetic;
    }

    public boolean isLessThanOrEqual() {
        return lessThanOrEqual;
    }

    public void setLessThanOrEqual(boolean lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
    }

    public boolean isLessThanOrEqualAlphabetic() {
        return lessThanOrEqualAlphabetic;
    }

    public void setLessThanOrEqualAlphabetic(boolean lessThanOrEqualAlphabetic) {
        this.lessThanOrEqualAlphabetic = lessThanOrEqualAlphabetic;
    }

    public boolean isGreaterThan() {
        return greaterThan;
    }

    public void setGreaterThan(boolean greaterThan) {
        this.greaterThan = greaterThan;
    }

    public boolean isGreaterThanAlphabetic() {
        return greaterThanAlphabetic;
    }

    public void setGreaterThanAlphabetic(boolean greaterThanAlphabetic) {
        this.greaterThanAlphabetic = greaterThanAlphabetic;
    }

    public boolean isGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    public void setGreaterThanOrEqual(boolean greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
    }

    public boolean isGreaterThanOrEqualAlphabetic() {
        return greaterThanOrEqualAlphabetic;
    }

    public void setGreaterThanOrEqualAlphabetic(boolean greaterThanOrEqualAlphabetic) {
        this.greaterThanOrEqualAlphabetic = greaterThanOrEqualAlphabetic;
    }

    public boolean isAnd() {
        return and;
    }

    public void setAnd(boolean and) {
        this.and = and;
    }

    public boolean isAndAlphabetic() {
        return andAlphabetic;
    }

    public void setAndAlphabetic(boolean andAlphabetic) {
        this.andAlphabetic = andAlphabetic;
    }

    public boolean isOr() {
        return or;
    }

    public void setOr(boolean or) {
        this.or = or;
    }

    public boolean isOrAlphabetic() {
        return orAlphabetic;
    }

    public void setOrAlphabetic(boolean orAlphabetic) {
        this.orAlphabetic = orAlphabetic;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public boolean isNotAlphabetic() {
        return notAlphabetic;
    }

    public void setNotAlphabetic(boolean notAlphabetic) {
        this.notAlphabetic = notAlphabetic;
    }

    @Override
    public String toString() {
        return "SpelRelational{" +
                "equal=" + equal +
                ", equalAlphabetic=" + equalAlphabetic +
                ", notEqual=" + notEqual +
                ", notEqualAlphabetic=" + notEqualAlphabetic +
                ", lessThan=" + lessThan +
                ", lessThanAlphabetic=" + lessThanAlphabetic +
                ", lessThanOrEqual=" + lessThanOrEqual +
                ", lessThanOrEqualAlphabetic=" + lessThanOrEqualAlphabetic +
                ", greaterThan=" + greaterThan +
                ", greaterThanAlphabetic=" + greaterThanAlphabetic +
                ", greaterThanOrEqual=" + greaterThanOrEqual +
                ", greaterThanOrEqualAlphabetic=" + greaterThanOrEqualAlphabetic +
                ", and=" + and +
                ", andAlphabetic=" + andAlphabetic +
                ", or=" + or +
                ", orAlphabetic=" + orAlphabetic +
                ", not=" + not +
                ", notAlphabetic=" + notAlphabetic +
                '}';
    }
}
