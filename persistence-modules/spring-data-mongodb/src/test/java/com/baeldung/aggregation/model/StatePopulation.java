package com.baeldung.aggregation.model;

import org.springframework.data.annotation.Id;

public class StatePopulation {

    @Id
    private String state;
    private Integer statePop;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStatePop() {
        return statePop;
    }

    public void setStatePop(Integer statePop) {
        this.statePop = statePop;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("StatePopulation [state=");
        builder.append(state);
        builder.append(", statePop=");
        builder.append(statePop);
        builder.append("]");
        return builder.toString();
    }

}
