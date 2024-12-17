package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring", uses = GenreMapper.class)
public interface GenreMapper {

    //Mapea desde GenreRequest (solicitud de creación) a la entidad Genre
    Genre toEntity(GenreRequest genreRequest);

    //Mapea desde Genre (entidad) a GenreResponse (respuesta)
    public GenreResponse toGenreResponse(Genre genre);

    // Método para mapear el estado (status) a un valor legible
    public default String mapStatus(Song song){
        return song.getStatus() ? "ACTIVE" : "INACTIVE";
    }
}
