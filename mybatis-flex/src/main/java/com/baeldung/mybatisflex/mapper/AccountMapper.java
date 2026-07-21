package com.baeldung.mybatisflex.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baeldung.mybatisflex.entity.Account;
import com.mybatisflex.core.BaseMapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
}
