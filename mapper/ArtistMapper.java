package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.ArtistRequest;
import com.music.Emotion.model.dto.ArtistResponse;
import com.music.Emotion.model.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring", uses = ArtistMapper.class)
public interface ArtistMapper {

    // Mapea desde ArtistRequest (solicitud de creación) a la entidad Artist
    Artist toEntity(ArtistRequest artistRequest);

    // Mapea desde Artist (entidad) a ArtistResponse (respuesta)
    @Mapping(target = "status", expression = "java(mapStatus(artist))")
    public ArtistResponse toArtistResponse(Artist artist);

    // Método para mapear el estado (status) a un valor legible
    public default String mapStatus(Artist artist){
        return artist.getStatus() ? "ACTIVE" : "INACTIVE";
    }
}
