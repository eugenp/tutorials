package com.baeldung.chaosmonkey.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by adi on 8/2/18.
 */
@Service
public class PermissionsService {

    public List<String> getAllPermissions() {
        return Arrays.asList("CREATE", "READ", "UPDATE", "DELETE");
    }
}
