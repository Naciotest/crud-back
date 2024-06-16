package com.rosada.crud.Mapper;

import com.rosada.crud.dto.UserDTO;
import com.rosada.crud.model.UserCrud;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapperUser(UserDTO dto, @MappingTarget UserCrud userCrud);
}
