package com.baeldung.rules.jess.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {
    String answer;
    int newBalance;
}
