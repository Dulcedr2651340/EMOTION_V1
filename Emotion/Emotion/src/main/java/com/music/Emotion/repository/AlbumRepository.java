package com.music.Emotion.repository;

import com.music.Emotion.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    List<Album> findAllByStatusTrue(); // Devuelve todas las canciones cuyo estado es true (activo)
}
