package com.baeldung.application.port.output;


import com.baeldung.application.domain.GamerAccount;


public interface GetAccountPort {

    GamerAccount load(Long id);

}
