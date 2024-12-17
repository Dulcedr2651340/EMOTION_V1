package com.music.Emotion.repository;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Album> findAllByStatusTrue(); // Devuelve todas las canciones cuyo estado es true (activo)
}
