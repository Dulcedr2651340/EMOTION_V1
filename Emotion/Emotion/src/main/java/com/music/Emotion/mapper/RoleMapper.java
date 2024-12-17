package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.RoleRequest;
import com.music.Emotion.model.dto.RoleResponse;
import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.model.entity.Role;
import com.music.Emotion.model.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring", uses = RoleMapper.class)
public interface RoleMapper {

    // Mapea desde RolRequest (solicitud de creación) a la entidad Role
    Role toEntity(RoleRequest roleRequest);

    // Mapea desde Role (entidad) a RoleResponse (respuesta)
    @Mapping(target = "status", expression = "java(mapStatus(role))")
    public RoleResponse toRoleResponse(Role role);

    // Método para mapear el estado (status) a un valor legible
    public default String mapStatus(Role role){
        return role.getStatus() ? "ACTIVE" : "INACTIVE";
    }

}
