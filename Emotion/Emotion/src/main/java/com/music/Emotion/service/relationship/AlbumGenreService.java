package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.repository.AlbumRepository;
import com.music.Emotion.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlbumGenreService {

    private final GenreRepository genreRepository;
    private final AlbumRepository albumRepository;

    public Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }
    public Set<Album> getAlbumsByIds(Set<Integer> albumIds) {
        return new HashSet<>(albumRepository.findAllById(albumIds));
    }
}
