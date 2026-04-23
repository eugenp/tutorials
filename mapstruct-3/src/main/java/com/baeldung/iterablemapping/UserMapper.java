package com.baeldung.iterablemapping;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;

@Mapper
public interface UserMapper {
    
    @IterableMapping(qualifiedByName = "mapLoginOnly", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT) 
    List<UserDto> toDto(List<User> users);
    
    @Named("mapLoginOnly") 
    default UserDto mapLoginOnly(User user) { 
        return user != null ? new UserDto(user.getLogin()) : null; 
    }

}
