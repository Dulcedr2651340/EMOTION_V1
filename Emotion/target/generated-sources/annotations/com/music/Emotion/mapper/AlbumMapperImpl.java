package com.music.Emotion.mapper;

import com.music.Emotion.model.dto.AlbumRequest;
import com.music.Emotion.model.dto.AlbumResponse;
import com.music.Emotion.model.entity.Album;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-03T19:51:37-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class AlbumMapperImpl implements AlbumMapper {

    @Override
    public Album toEntity(AlbumRequest albumRequest) {
        if ( albumRequest == null ) {
            return null;
        }

        Album album = new Album();

        album.setTitle( albumRequest.getTitle() );
        album.setReleaseDate( albumRequest.getReleaseDate() );
        album.setDescription( albumRequest.getDescription() );

        return album;
    }

    @Override
    public AlbumResponse toAlbumResponse(Album album) {
        if ( album == null ) {
            return null;
        }

        AlbumResponse.AlbumResponseBuilder albumResponse = AlbumResponse.builder();

        albumResponse.id( album.getId() );
        albumResponse.title( album.getTitle() );
        albumResponse.releaseDate( album.getReleaseDate() );
        albumResponse.description( album.getDescription() );

        albumResponse.status( mapStatus(album) );

        return albumResponse.build();
    }
}
