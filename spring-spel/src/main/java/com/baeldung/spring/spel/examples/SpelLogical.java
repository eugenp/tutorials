package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelLogical")
public class SpelLogical {
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
        return "SpelLogical{" +
                "and=" + and +
                ", andAlphabetic=" + andAlphabetic +
                ", or=" + or +
                ", orAlphabetic=" + orAlphabetic +
                ", not=" + not +
                ", notAlphabetic=" + notAlphabetic +
                '}';
    }
}
