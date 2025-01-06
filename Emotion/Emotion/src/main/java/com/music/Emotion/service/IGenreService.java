package com.music.Emotion.service;

import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.entity.Genre;

import java.util.List;
import java.util.Set;

public interface IGenreService {

    //Listar todos los géneros
    List<GenreResponse> getAllGenres();

    //Guardar un nuevo género
    GenreResponse saveGenre(GenreRequest genreRequest);

    //Actualizar un género existente
    GenreResponse updateGenre(Integer id, GenreRequest genreRequest);

    //Buscar un género por su ID
    GenreResponse findById(Integer id);

    //Obtener géneros por un conjunto de IDs
    Set<Genre> getGenresByIds(Set<Integer> genreIds);

    // Eliminar un género por su ID
    void deleteGenreById(Integer id);
}