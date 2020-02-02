package com.baeldung.hexagonalarchitecture.application.boundary.driverports;

public interface ICommandOperator {
        void reactTo(Object command);
}
