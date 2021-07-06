package com.baeldung.hexagonalarchitecture.notification.usecase;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReaderDto {
    Long id;
    String name;
    String email;
    String phone;
}
