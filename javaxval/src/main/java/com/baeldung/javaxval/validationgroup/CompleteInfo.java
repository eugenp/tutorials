package com.baeldung.javaxval.validationgroup;

import javax.validation.GroupSequence;

@GroupSequence({ BasicInfo.class, AdvanceInfo.class })
public interface CompleteInfo {

}
