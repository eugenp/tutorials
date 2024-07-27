package com.baeldung.mybatisplus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baeldung.mybatisplus.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}