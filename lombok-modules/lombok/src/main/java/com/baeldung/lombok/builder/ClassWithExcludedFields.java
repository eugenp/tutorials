package com.baeldung.lombok.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassWithExcludedFields {

    private int id;
    private String includedField;

    @Builder.Default
    private String excludedField = "Excluded Field using Default";

    static String anotherExcludedField = "Excluded Field using static";

    @Builder(builderMethodName = "customBuilder")
    public static ClassWithExcludedFields of(int id, String includedField) {
        ClassWithExcludedFields myObject = new ClassWithExcludedFields();
        myObject.setId(id);
        myObject.setIncludedField(includedField);

        return myObject;
    }

}
