package com.baeldung.shallowDeepCopyExamples.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EmployerDetails implements Serializable {
    private String employerName;
}
