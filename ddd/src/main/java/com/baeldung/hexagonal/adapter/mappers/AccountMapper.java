package com.baeldung.hexagonal.adapter.mappers;

import com.baeldung.hexagonal.adapter.entity.Account;
import com.baeldung.hexagonal.domain.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto accountToAccountDto(Account account);

    Account accountDtoToAccount(AccountDto accountDto);
}
