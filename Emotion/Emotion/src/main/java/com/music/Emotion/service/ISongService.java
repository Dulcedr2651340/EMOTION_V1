package com.music.Emotion.service;

import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.model.entity.Song;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ISongService {

    // Listar todas las canciones con sus detalles
    // List<> getAllDetailedSongs();

    // Listar solo las canciones
    List<SongResponse> getAllSongs();

    // Guardar las canciones
     public SongResponse saveSong(SongRequest songRequest);

    // Actualizar una canción existente
    public SongResponse updateSong(Integer Id, SongRequest songRequest);

    // Buscar una canción por su ID
    public SongResponse findById(Integer id);

    // Obtener canciones por un conjunto de IDs
    Set<Song> getSongsByIds(Set<Integer> songIds);

    public List<SongResponse> findAllBySong();

    // Eliminar lógicamente una canción existente
     public void deleteSongById(Integer id);

}
