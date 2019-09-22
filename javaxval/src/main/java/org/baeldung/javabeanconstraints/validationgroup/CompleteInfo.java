package org.baeldung.javabeanconstraints.validationgroup;

import javax.validation.GroupSequence;

@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public interface CompleteInfo {

}
