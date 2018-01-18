package com.baeldung.kong.domain;

/**
 * @author aiet
 */
public class TargetObject {

    public TargetObject(String target, int weight) {
        this.target = target;
        this.weight = weight;
    }

    private String target;
    private int weight;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
