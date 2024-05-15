package com.baeldung.algorithms.enumstatemachine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LeaveRequestStateUnitTest {

    @Test
    void givenLeaveRequest_whenStateEscalated_thenResponsibleIsTeamLeader() {
        LeaveRequestState state = LeaveRequestState.Escalated;

        assertEquals( "Team Leader", state.responsiblePerson());
    }


    @Test
    void givenLeaveRequest_whenStateApproved_thenResponsibleIsDepartmentManager() {
        LeaveRequestState state = LeaveRequestState.Approved;

        assertEquals( "Department Manager" , state.responsiblePerson());
    }

    @Test
    void givenLeaveRequest_whenNextStateIsCalled_thenStateIsChanged() {
        LeaveRequestState state = LeaveRequestState.Submitted;

        state = state.nextState();
        assertEquals(LeaveRequestState.Escalated, state);

        state = state.nextState();
        assertEquals(LeaveRequestState.Approved, state);

        state = state.nextState();
        assertEquals(LeaveRequestState.Approved, state);
    }
}
