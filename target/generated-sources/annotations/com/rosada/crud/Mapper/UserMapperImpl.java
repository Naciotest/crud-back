package com.rosada.crud.Mapper;

import com.rosada.crud.dto.UserDTO;
import com.rosada.crud.model.UserCrud;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-16T21:46:08+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void mapperUser(UserDTO dto, UserCrud userCrud) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            userCrud.setName( dto.getName() );
        }
        if ( dto.getEmail() != null ) {
            userCrud.setEmail( dto.getEmail() );
        }
        if ( dto.getCountry() != null ) {
            userCrud.setCountry( dto.getCountry() );
        }
        if ( dto.getState() != null ) {
            userCrud.setState( dto.getState() );
        }
    }
}
