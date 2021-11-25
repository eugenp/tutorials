package com.baeldung.application.port.output;

import com.baeldung.application.domain.GamerAccount;

public interface SaveAccountPort {

    void save(GamerAccount gamerAccount);

}
