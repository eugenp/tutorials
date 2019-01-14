package com.baeldung.hexagonal.core.mapper;

import com.baeldung.hexagonal.core.contract.dto.UserDTO;
import com.baeldung.hexagonal.core.contract.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CoreModelMapper extends ConfigurableMapper {

    public void configure(MapperFactory factory) {
        super.configure(factory);

        factory.registerClassMap(factory.classMap(UserDTO.class, User.class)
            .customize(new CustomMapper<UserDTO, User>() {
                @Override
                public void mapAtoB(UserDTO userDTO, User user, MappingContext context) {
                    user.setId(userDTO.getId());
                    user.setName(userDTO.getName());
                }
            })
            .byDefault()
            .toClassMap());

        factory.registerClassMap(factory.classMap(User.class, UserDTO.class)
            .customize(new CustomMapper<User, UserDTO>() {
                @Override
                public void mapAtoB(User user, UserDTO userDTO, MappingContext context) {
                    userDTO.setId(user.getId());
                    userDTO.setName(user.getName());
                }
            })
            .byDefault()
            .toClassMap());

    }

}