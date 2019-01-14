package com.baeldung.hexagonal.adapter.data.mapper;

import com.baeldung.hexagonal.adapter.data.entity.UserEntity;
import com.baeldung.hexagonal.core.contract.dto.UserDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper extends ConfigurableMapper {

    public void configure(MapperFactory factory) {
        super.configure(factory);

        factory.registerClassMap(factory.classMap(UserDTO.class, UserEntity.class)
            .customize(new CustomMapper<UserDTO, UserEntity>() {
                @Override
                public void mapAtoB(UserDTO userDTO, UserEntity userEntity, MappingContext context) {
                    userEntity.setId(userDTO.getId());
                    userEntity.setName(userDTO.getName());
                }
            })
            .byDefault()
            .toClassMap());

        factory.registerClassMap(factory.classMap(UserEntity.class, UserDTO.class)
            .customize(new CustomMapper<UserEntity, UserDTO>() {
                @Override
                public void mapAtoB(UserEntity userEntity, UserDTO userDTO, MappingContext context) {
                    userDTO.setId(userEntity.getId());
                    userDTO.setName(userEntity.getName());
                }
            })
            .byDefault()
            .toClassMap());

    }

}