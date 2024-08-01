package com.baeldung.mapstruct.date.mapper;

import com.baeldung.mapstruct.date.model.User;
import com.baeldung.mapstruct.date.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface UserMapper {

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "yyyy-MM-dd")
    User toUser(UserDTO userDTO);

    @Mapping(source = "birthDate", target = "birthDate")
    User toUserCustom(UserDTO userDTO);

    default Date mapStringToDate(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}