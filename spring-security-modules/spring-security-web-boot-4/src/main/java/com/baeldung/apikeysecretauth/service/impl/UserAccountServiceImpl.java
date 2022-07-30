package com.baeldung.apikeysecretauth.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baeldung.apikeysecretauth.model.User;
import com.baeldung.apikeysecretauth.model.UserKeys;
import com.baeldung.apikeysecretauth.repository.UserKeysRepository;
import com.baeldung.apikeysecretauth.repository.UserRepository;
import com.baeldung.apikeysecretauth.repository.model.UserData;
import com.baeldung.apikeysecretauth.repository.model.UserKeysData;
import com.baeldung.apikeysecretauth.service.UserAccountService;
import com.baeldung.apikeysecretauth.util.KeyGeneratorUtils;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KeyGeneratorUtils keyGeneratorUtils;
    private final UserKeysRepository userKeysRepository;

    public UserAccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, KeyGeneratorUtils keyGeneratorUtils, UserKeysRepository userKeysRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.keyGeneratorUtils = keyGeneratorUtils;
        this.userKeysRepository = userKeysRepository;
    }

    @Override
    public UserKeys generateApiKeys(User user) {
        UserData dbUser = userRepository.findById(user.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            final String apiKey = UUID.randomUUID().toString();
            final String apiSecret = keyGeneratorUtils.generateSecret(apiKey, dbUser.getPassword());

            UserKeysData userKeysData = new UserKeysData();
            userKeysData.setApiKey(apiKey);
            userKeysData.setApiSecret(passwordEncoder.encode(apiSecret));
            userKeysData.setAppUser(dbUser);
            userKeysRepository.save(userKeysData);

            return new UserKeys(apiKey, apiSecret);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate user keys", e);
        }
    }
}
