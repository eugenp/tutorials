package com.baeldung.hexagonalarchitecture.domain;

public class StaffMembersNotification {

    public enum Team {
        CLEANING, MAINTENANCE, RECEPTION, ACCOUNTING
    }

    private Team team;
    private String roomNumber;

    public StaffMembersNotification(Team team, String roomNumber) {
        this.team = team;
        this.roomNumber = roomNumber;
    }

    public Team getTeam() {
        return team;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}
