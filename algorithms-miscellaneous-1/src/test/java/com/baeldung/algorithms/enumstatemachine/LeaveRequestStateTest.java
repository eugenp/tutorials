package com.baeldung.algorithms.enumstatemachine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeaveRequestStateTest {

    @Test
    public void givenLeaveRequest_whenStateEscalated_thenResponsibleIsTeamLeader() {
        LeaveRequestState state = LeaveRequestState.Escalated;

        String responsible = state.responsiblePerson();

        assertEquals(responsible, "Team Leader");
    }


    @Test
    public void givenLeaveRequest_whenStateApproved_thenResponsibleIsDepartmentManager() {
        LeaveRequestState state = LeaveRequestState.Approved;

        String responsible = state.responsiblePerson();

        assertEquals(responsible, "Department Manager");
    }

    @Test
    public void givenLeaveRequest_whenNextStateIsCalled_thenStateIsChanged() {
        LeaveRequestState state = LeaveRequestState.Submitted;

        state = state.nextState();
        assertEquals(state, LeaveRequestState.Escalated);

        state = state.nextState();
        assertEquals(state, LeaveRequestState.Approved);

        state = state.nextState();
        assertEquals(state, LeaveRequestState.Approved);
    }
}
