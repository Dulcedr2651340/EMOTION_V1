package com.music.Emotion.repository;

import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {

    // Devuelve todos los géneros cuyo estado es true (activo)
    List<Genre> findAllByStatusTrue();

}
