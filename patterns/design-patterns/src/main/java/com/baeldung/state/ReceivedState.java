package com.baeldung.state;

public class ReceivedState implements PackageState {

    @Override
    public void next(Package pkg) {
        if (pkg.getState() instanceof ReceivedState) {
            System.out.println("This package is already received by a client.");
            return;
        }
        pkg.setState(this);
    }

    @Override
    public void prev(Package pkg) {
        pkg.setState(new DeliveredState());
    }

    @Override
    public void printStatus() {
        System.out.println("Package was received by client.");
    }

    @Override
    public String toString() {
        return "ReceivedState{}";
    }
}