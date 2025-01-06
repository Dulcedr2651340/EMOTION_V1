package com.music.Emotion.repository;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    List<Artist> findAllByStatusTrue(); // Devuelve todas las canciones cuyo estado es true (activo)
}
