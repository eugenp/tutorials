package com.baeldung.filenameandclassname;

/*
 Filename: FootballGame.java

 The code doesn't compile if we turn FootballPlayer to public,
 unless we rename the file to FootballPlayer.java
 */
class FootballPlayer {

    private String name;
    private Club club;

    public FootballPlayer(String name, Club club) {
        this.name = name;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public Club getClub() {
        return club;
    }
}

class Club {

    private String name;

    public Club(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}