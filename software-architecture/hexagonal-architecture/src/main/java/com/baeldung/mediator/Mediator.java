package com.baeldung.mediator;

import com.baeldung.port.UserInterfacePort;

public interface Mediator<Req extends MediatorRequest> {

    void execute(Req request, UserInterfacePort userInterfacePort) throws Exception;

    void discard();
}
