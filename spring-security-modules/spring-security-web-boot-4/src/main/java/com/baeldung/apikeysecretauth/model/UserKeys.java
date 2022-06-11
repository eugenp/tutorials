package com.baeldung.apikeysecretauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserKeys {
    private String apiKey;
    private String apiSecret;
}
