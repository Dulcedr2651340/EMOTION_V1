package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Artist;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.repository.ArtistRepository;
import com.music.Emotion.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistGenreService {

    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;

    public Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }
    public Set<Artist> getArtistByIds(Set<Integer> artistIds) {
        return new HashSet<>(artistRepository.findAllById(artistIds));
    }

}
