package com.baeldung.multipledatamodules.redis;

import org.springframework.data.repository.CrudRepository;

public interface BookLoanRepository extends CrudRepository<BookLoan, String>{

}
