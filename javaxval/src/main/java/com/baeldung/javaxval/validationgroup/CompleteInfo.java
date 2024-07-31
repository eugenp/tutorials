package com.baeldung.javaxval.validationgroup;

import jakarta.validation.GroupSequence;

@GroupSequence({ BasicInfo.class, AdvanceInfo.class })
public interface CompleteInfo {

}
