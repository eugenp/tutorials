package com.baeldung.lombok.constructor;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LombokConstructorWithRequiredArgsAndFinal {
    final String name;

    final int age;

    final List <String> emailIds = new ArrayList <>();
}