package com.baeldung.mybatis.generatedid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface CarMapper {

    @Insert("INSERT INTO CAR(MODEL) values (#{model})")
    @Options(useGeneratedKeys = true, keyColumn = "ID", keyProperty = "id")
    void saveUsingOptions(Car car);

    @Insert("INSERT INTO CAR(MODEL) values (#{model})")
    @SelectKey(statement = "CALL IDENTITY()", before = false, keyColumn = "ID", keyProperty = "id", resultType = Long.class)
    void saveUsingSelectKey(Car car);
}
