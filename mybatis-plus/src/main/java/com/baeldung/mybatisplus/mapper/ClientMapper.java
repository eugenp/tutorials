package com.baeldung.mybatisplus.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baeldung.mybatisplus.entity.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {

}