package com.baeldung.port;

import com.baeldung.mediator.MediatorResponse;

public interface UserInterfacePort {

    void accept(MediatorResponse response);
}
