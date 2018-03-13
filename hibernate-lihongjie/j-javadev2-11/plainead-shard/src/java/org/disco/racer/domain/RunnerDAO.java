package org.disco.racer.domain;

import java.util.Collection;

interface RunnerDAO {
    Collection<Runner> findByFirstAndLastName(String fname, String lname);

    Runner findById(Long id);

    void create(Runner runner);

    void update(Runner runner);

    void remove(Runner runner);
}
