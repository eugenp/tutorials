package com.baeldung.bindcustomvalidationmessage.customvalidator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordDTO {
    @ValidPassword
    private String password;
}
