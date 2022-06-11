package com.baeldung.apikeysecretauth.service;

import com.baeldung.apikeysecretauth.model.User;
import com.baeldung.apikeysecretauth.model.UserKeys;

public interface UserAccountService {
    UserKeys generateApiKeys(User user);
}
