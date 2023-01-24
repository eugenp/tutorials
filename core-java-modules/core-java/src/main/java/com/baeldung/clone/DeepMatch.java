package com.baeldung.clone;

import java.time.OffsetDateTime;

public class DeepMatch extends ShallowMatch {

    public DeepMatch(Type type, OffsetDateTime startTime, OffsetDateTime endTime, Integer durationInMinutes, PlayersDetail playersDetail) {
        super(type, startTime, endTime, durationInMinutes, playersDetail);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepMatch deepCopyMatch = (DeepMatch) super.clone();
        deepCopyMatch.setPlayersDetail((PlayersDetail) getPlayersDetail().clone());
        return deepCopyMatch;
    }
}
