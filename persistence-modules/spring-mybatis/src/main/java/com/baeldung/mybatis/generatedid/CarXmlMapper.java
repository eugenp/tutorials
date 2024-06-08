package com.baeldung.mybatis.generatedid;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarXmlMapper {

    void saveUsingOptions(Car car);

    void saveUsingSelectKey(Car car);

}
