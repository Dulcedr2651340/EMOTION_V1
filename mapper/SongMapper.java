package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.model.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring", uses = SongMapper.class)
public interface SongMapper {

    // Mapea desde SongRequest (solicitud de creación) a la entidad Song
    Song toEntity(SongRequest songRequest);

    // Mapea desde Song (entidad) a SongResponse (respuesta)
    @Mapping(target = "status", expression = "java(mapStatus(song))")
    public SongResponse toSongResponse(Song song);

    // Método para mapear el estado (status) a un valor legible
    public default String mapStatus(Song song){
        return song.getStatus() ? "ACTIVE" : "INACTIVE";
    }
}
