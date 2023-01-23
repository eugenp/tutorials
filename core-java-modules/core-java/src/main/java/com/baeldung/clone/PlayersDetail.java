package com.baeldung.clone;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayersDetail implements Cloneable {

    private String player1Name;

    private String player2Name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
