package com.baeldung.chainedexception;

import com.baeldung.chainedexception.exceptions.GirlFriendOfManagerUpsetException;
import com.baeldung.chainedexception.exceptions.ManagerUpsetException;
import com.baeldung.chainedexception.exceptions.NoLeaveGrantedException;
import com.baeldung.chainedexception.exceptions.TeamLeadUpsetException;

public class LogWithoutChain {

    public static void main(String[] args) throws Exception {
        getLeave();
    }

    private static void getLeave() throws NoLeaveGrantedException {
        try {
            howIsTeamLead();
        } catch (TeamLeadUpsetException e) {
            e.printStackTrace();
            throw new NoLeaveGrantedException("Leave not sanctioned.");
        }
    }

    private static void howIsTeamLead() throws TeamLeadUpsetException {
        try {
            howIsManager();
        } catch (ManagerUpsetException e) {
            e.printStackTrace();
            throw new TeamLeadUpsetException("Team lead is not in good mood");
        }
    }

    private static void howIsManager() throws ManagerUpsetException {
        try {
            howIsGirlFriendOfManager();
        } catch (GirlFriendOfManagerUpsetException e) {
            e.printStackTrace();
            throw new ManagerUpsetException("Manager is in bad mood");
        }
    }

    private static void howIsGirlFriendOfManager() throws GirlFriendOfManagerUpsetException {
        throw new GirlFriendOfManagerUpsetException("Girl friend of manager is in bad mood");
    }
}
