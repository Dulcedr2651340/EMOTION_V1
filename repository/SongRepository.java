package com.music.Emotion.repository;

import com.music.Emotion.model.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * --------------------------------------------------
 * Repositorio de Song que maneja las operaciones de
 * persistencia para la entidad Song.
 * Extiende JpaRepository para utilizar métodos
 * CRUD y consultas personalizadas.
 * --------------------------------------------------
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {


    List<Song> findAllByStatusTrue(); // Devuelve todas las canciones cuyo estado es true (activo)
}
