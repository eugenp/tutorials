package com.baeldung.jackson.deductionbasedpolymorphism;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.DEDUCTION)
@JsonSubTypes({ @Type(ImperialSpy.class), @Type(King.class), @Type(Knight.class) })
public interface Character {

}
