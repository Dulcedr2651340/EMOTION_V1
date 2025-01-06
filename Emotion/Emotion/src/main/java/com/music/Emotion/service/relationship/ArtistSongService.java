package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Artist;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import com.music.Emotion.repository.ArtistRepository;
import com.music.Emotion.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistSongService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public Set<Artist> getArtistsByIds(Set<Integer> artistIds) {
        return new HashSet<>(artistRepository.findAllById(artistIds));
    }
    public Set<Song> getSongsByIds(Set<Integer> songIds) {
        return new HashSet<>(songRepository.findAllById(songIds));
    }

}
