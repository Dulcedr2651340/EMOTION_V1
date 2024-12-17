package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.AlbumRequest;
import com.music.Emotion.model.dto.AlbumResponse;
import com.music.Emotion.model.entity.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring", uses = AlbumMapper.class)
public interface AlbumMapper {

    // Mapea desde AlbumRequest (solicitud de creación) a la entidad Album
    Album toEntity(AlbumRequest albumRequest);

    // Mapea desde Album (entidad) a AlbumResponse (respuesta)
    @Mapping(target = "status", expression = "java(mapStatus(album))")
    public AlbumResponse toAlbumResponse(Album album);

    // Método para mapear el estado (status) a un valor legible
    public default String mapStatus(Album album){
        return album.getStatus() ? "ACTIVE" : "INACTIVE";
    }
}
