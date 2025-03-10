package com.baeldung.configuremockbean;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String getUserName(Long id);
}
