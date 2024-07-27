package com.baeldung.mybatisplus.mapper;

import com.baeldung.mybatisplus.entity.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {

}