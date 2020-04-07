package com.baeldung.endpoints.info;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class TotalUsersInfoContributor implements InfoContributor {

    @Autowired
    UserRepository userRepository;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> userDetails = new HashMap<>();
        userDetails.put("active", userRepository.countByStatus(1));
        userDetails.put("inactive", userRepository.countByStatus(0));

        builder.withDetail("users", userDetails);
    }
}
