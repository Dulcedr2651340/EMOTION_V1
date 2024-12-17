package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.model.entity.Song;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:51:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class SongMapperImpl implements SongMapper {

    @Override
    public Song toEntity(SongRequest songRequest) {
        if ( songRequest == null ) {
            return null;
        }

        Song song = new Song();

        song.setName( songRequest.getName() );
        song.setDuration( songRequest.getDuration() );
        song.setRating( songRequest.getRating() );

        return song;
    }

    @Override
    public SongResponse toSongResponse(Song song) {
        if ( song == null ) {
            return null;
        }

        SongResponse.SongResponseBuilder songResponse = SongResponse.builder();

        songResponse.id( song.getId() );
        songResponse.name( song.getName() );
        songResponse.duration( song.getDuration() );
        if ( song.getRating() != null ) {
            songResponse.rating( song.getRating().doubleValue() );
        }

        songResponse.status( mapStatus(song) );

        return songResponse.build();
    }
}
