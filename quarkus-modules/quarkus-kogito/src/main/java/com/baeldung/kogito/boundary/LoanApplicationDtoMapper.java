package com.baeldung.kogito.boundary;

import com.baeldung.kogito.boundary.model.LoanApplicationDto;
import com.baeldung.kogito.rules.model.LoanApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface LoanApplicationDtoMapper {

    LoanApplication map(LoanApplicationDto loanApplicationDto);

    LoanApplicationDto map(LoanApplication loanApplication);

}
