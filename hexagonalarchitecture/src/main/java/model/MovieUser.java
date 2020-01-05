package model;

import driverports.IUserInput;

public class MovieUser {
    private IUserInput userInputDriverPort;

    public MovieUser(IUserInput userInputDriverPort) {
        this.userInputDriverPort = userInputDriverPort;
    }

    public void processInput(MovieSearchRequest movieSearchRequest) {
        userInputDriverPort.handleUserInput(movieSearchRequest);
    }
}
