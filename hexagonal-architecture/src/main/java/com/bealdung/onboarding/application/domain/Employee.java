package com.bealdung.onboarding.application.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private Long id;
    private String name;
    private String family;
    public String getEmployeeInfo() {
        return "name : " + getName() + " family : " + getFamily() + " ID : " + getId();
    }
}
