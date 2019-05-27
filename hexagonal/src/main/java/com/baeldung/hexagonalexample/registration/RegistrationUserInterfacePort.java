package com.baeldung.hexagonalexample.registration;

import java.util.List;

interface RegistrationUserInterfacePort {

    List<Registration> fetchAllRegistrations();

    Registration register(String emailAddress);
}
