package com.baeldung.kogito.boundary;

import org.mapstruct.Mapper;

import com.baeldung.kogito.boundary.model.LoanApplicationDto;
import com.baeldung.kogito.rules.model.LoanApplication;

@Mapper(componentModel = "cdi")
public interface LoanApplicationDtoMapper {

    LoanApplication map(LoanApplicationDto loanApplicationDto);

    LoanApplicationDto map(LoanApplication loanApplication);

}
