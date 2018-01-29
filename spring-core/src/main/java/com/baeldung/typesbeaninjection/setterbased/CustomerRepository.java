package com.baeldung.typesbeaninjection.setterbased;

import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    String findOne() {
        return "Customer";
    }

}
