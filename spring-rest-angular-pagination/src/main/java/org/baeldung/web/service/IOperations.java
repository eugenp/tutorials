package org.baeldung.web.service;

import org.springframework.data.domain.Page;

public interface IOperations<T> {

	Page<T> findPaginated(int page, int size);

}
