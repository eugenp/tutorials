package com.baeldung.inprogress.hexagonal.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Account {
    private String id;
    private String name;
}
