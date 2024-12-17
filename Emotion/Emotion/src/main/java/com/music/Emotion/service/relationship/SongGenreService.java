package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import com.music.Emotion.repository.GenreRepository;
import com.music.Emotion.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SongGenreService {

    private final SongRepository songRepository;
    private final GenreRepository genreRepository;

    public Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        return new HashSet<>(genreRepository.findAllById(genreIds));
    }

    public Set<Song> getSongsByIds(Set<Integer> songIds) {
        return new HashSet<>(songRepository.findAllById(songIds));
    }
}
