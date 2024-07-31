package com.baeldung.pattern.richdomainmodel;

public class TennisGame {
    private final Player server;
    private final Player receiver;

    public TennisGame(String server, String receiver) {
        this.server = new Player(server);
        this.receiver = new Player(receiver);
    }

    public void wonPoint(String playerName) {
        if(server.name().equals(playerName)) {
            server.gainPoint();
        } else {
            receiver.gainPoint();
        }
    }

    public String getScore() {
        if (gameContinues()) {
            return getGameScore();
        }
        return "Win for " + leadingPlayer().name();
    }

    private String getGameScore() {
        if (isScoreEqual()) {
            return getEqualScore();
        }
        if (isAdvantage()) {
            return "Advantage " + leadingPlayer().name();
        }
        return getSimpleScore();
    }

    private boolean isScoreEqual() {
        return server.pointsDifference(receiver) == 0;
    }

    private boolean isAdvantage() {
        return leadingPlayer().hasScoreBiggerThan(Score.FORTY)
          && Math.abs(server.pointsDifference(receiver)) == 1;
    }

    private boolean isGameFinished() {
        return leadingPlayer().hasScoreBiggerThan(Score.FORTY)
          && Math.abs(server.pointsDifference(receiver)) >= 2;
    }

    private Player leadingPlayer() {
        if (server.pointsDifference(receiver) > 0) {
            return server;
        }
        return receiver;
    }

    private boolean gameContinues() {
        return !isGameFinished();
    }
    private String getSimpleScore() {
        return String.format("%s-%s", server.score(), receiver.score());
    }

    private String getEqualScore() {
        if (server.hasScoreBiggerThan(Score.THIRTY)) {
            return "Deuce";
        }
        return String.format("%s-All", server.score());
    }
}
