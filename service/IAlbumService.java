package com.music.Emotion.service;

import com.music.Emotion.model.dto.AlbumRequest;
import com.music.Emotion.model.dto.AlbumResponse;
import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Genre;

import java.util.List;
import java.util.Set;

public interface IAlbumService {


    //Listar todos los Albums
    List<AlbumResponse> getAllAlbums();

    //Guardar un nuevo Album
    AlbumResponse saveAlbum(AlbumRequest albumRequest);

    //Actualizar un Album existente
    AlbumResponse updateAlbum(Integer id, AlbumRequest albumRequest);

    //Buscar un Album por su ID
    AlbumResponse findById(Integer id);

    //Obtener album por un conjunto de IDs
    Set<Album> getAlbumsByIds(Set<Integer> albumIds);

    //Obtener genre por un conjunto de IDs
    Set<Genre> getGenresByIds(Set<Integer> genreIds);

    // Eliminar un Album por su ID
    void deleteAlbumById(Integer id);
}
