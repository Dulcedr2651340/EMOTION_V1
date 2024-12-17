package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.ArtistRequest;
import com.music.Emotion.model.dto.ArtistResponse;
import com.music.Emotion.model.entity.Artist;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:51:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class ArtistMapperImpl implements ArtistMapper {

    @Override
    public Artist toEntity(ArtistRequest artistRequest) {
        if ( artistRequest == null ) {
            return null;
        }

        Artist artist = new Artist();

        artist.setName( artistRequest.getName() );
        artist.setStageName( artistRequest.getStageName() );
        artist.setType( artistRequest.getType() );
        artist.setCountry( artistRequest.getCountry() );
        artist.setDebutDate( artistRequest.getDebutDate() );

        return artist;
    }

    @Override
    public ArtistResponse toArtistResponse(Artist artist) {
        if ( artist == null ) {
            return null;
        }

        ArtistResponse.ArtistResponseBuilder artistResponse = ArtistResponse.builder();

        artistResponse.id( artist.getId() );
        artistResponse.name( artist.getName() );
        artistResponse.stageName( artist.getStageName() );
        artistResponse.type( artist.getType() );
        artistResponse.country( artist.getCountry() );
        artistResponse.debutDate( artist.getDebutDate() );

        artistResponse.status( mapStatus(artist) );

        return artistResponse.build();
    }
}
