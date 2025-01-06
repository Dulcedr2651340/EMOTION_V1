package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.RoleRequest;
import com.music.Emotion.model.dto.RoleResponse;
import com.music.Emotion.model.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:51:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleRequest roleRequest) {
        if ( roleRequest == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( roleRequest.getName() );
        role.setStageName( roleRequest.getStageName() );
        role.setType( roleRequest.getType() );
        role.setCountry( roleRequest.getCountry() );
        role.setDebutDate( roleRequest.getDebutDate() );

        return role;
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponse.RoleResponseBuilder roleResponse = RoleResponse.builder();

        roleResponse.id( role.getId() );
        roleResponse.name( role.getName() );
        roleResponse.stageName( role.getStageName() );
        roleResponse.type( role.getType() );
        roleResponse.country( role.getCountry() );
        roleResponse.debutDate( role.getDebutDate() );

        roleResponse.status( mapStatus(role) );

        return roleResponse.build();
    }
}
