package com.baeldung.algorithms.enumstatemachine;

public enum LeaveRequestState {

    Submitted {
        @Override
        public LeaveRequestState nextState() {
            System.out.println("Starting the Leave Request and sending to Team Leader for approval.");
            return Escalated;
        }

        @Override
        public String responsiblePerson() {
            return "Employee";
        }
    },
    Escalated {
        @Override
        public LeaveRequestState nextState() {
            System.out.println("Reviewing the Leave Request and escalating to Department Manager.");
            return Approved;
        }

        @Override
        public String responsiblePerson() {
            return "Team Leader";
        }
    },
    Approved {
        @Override
        public LeaveRequestState nextState() {
            System.out.println("Approving the Leave Request.");
            return this;
        }

        @Override
        public String responsiblePerson() {
            return "Department Manager";
        }
    };

    public abstract String responsiblePerson();

    public abstract LeaveRequestState nextState();

}
