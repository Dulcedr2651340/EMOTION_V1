package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Artist;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.repository.AlbumRepository;
import com.music.Emotion.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistAlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public Set<Album> getAlbumByIds(Set<Integer> albumIds) {
        return new HashSet<>(albumRepository.findAllById(albumIds));
    }
    public Set<Artist> getArtistByIds(Set<Integer> artistIds) {
        return new HashSet<>(artistRepository.findAllById(artistIds));
    }
}
