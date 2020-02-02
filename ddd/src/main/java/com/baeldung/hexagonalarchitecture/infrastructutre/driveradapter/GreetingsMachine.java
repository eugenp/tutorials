package com.baeldung.hexagonalarchitecture.infrastructutre.driveradapter;

import com.baeldung.hexagonalarchitecture.application.boundary.driverports.ICommandOperator;
import com.baeldung.hexagonalarchitecture.domain.command.RequestGreeting;

/**
 * The driver adapter. It's on the left side of the hexagon. It sends
 * requests as commands to a driver port on the hexagon boundary.
 */
public class GreetingsMachine {
        private ICommandOperator driverPort;

        public GreetingsMachine(ICommandOperator driverPort) {
                this.driverPort = driverPort;
        }
        public void run() {
                driverPort.reactTo(new RequestGreeting("fr"));
                driverPort.reactTo(new RequestGreeting("en"));
        }
}
