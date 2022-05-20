package com.baeldung.algorithms.enumstatemachine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeaveRequestStateUnitTest {

    @Test
    public void givenLeaveRequest_whenStateEscalated_thenResponsibleIsTeamLeader() {
        LeaveRequestState state = LeaveRequestState.Escalated;

        assertEquals(state.responsiblePerson(), "Team Leader");
    }


    @Test
    public void givenLeaveRequest_whenStateApproved_thenResponsibleIsDepartmentManager() {
        LeaveRequestState state = LeaveRequestState.Approved;

        assertEquals(state.responsiblePerson(), "Department Manager");
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
