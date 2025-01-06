package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.entity.Genre;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:51:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class GenreMapperImpl implements GenreMapper {

    @Override
    public Genre toEntity(GenreRequest genreRequest) {
        if ( genreRequest == null ) {
            return null;
        }

        Genre genre = new Genre();

        genre.setName( genreRequest.getName() );
        genre.setDescription( genreRequest.getDescription() );

        return genre;
    }

    @Override
    public GenreResponse toGenreResponse(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreResponse.GenreResponseBuilder genreResponse = GenreResponse.builder();

        genreResponse.id( genre.getId() );
        genreResponse.name( genre.getName() );
        genreResponse.description( genre.getDescription() );

        return genreResponse.build();
    }
}
