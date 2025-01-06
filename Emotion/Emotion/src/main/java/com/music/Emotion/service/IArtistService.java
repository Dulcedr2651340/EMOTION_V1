package com.music.Emotion.service;

import com.music.Emotion.model.dto.ArtistRequest;
import com.music.Emotion.model.dto.ArtistResponse;
import com.music.Emotion.model.entity.Artist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface IArtistService {

    // Listar todos los artistas
    List<ArtistResponse> getAllArtists();

    // Guardar un nuevo artista
    public ArtistResponse saveArtist(ArtistRequest artistRequest);

    // Actualizar un artista existente
    public ArtistResponse updateArtist(Integer id, ArtistRequest artistRequest);

    // Buscar un artista por su ID
    public ArtistResponse findById(Integer id);

    // Obtener artistas por un conjunto de IDs
    Set<Artist> getArtistsByIds(Set<Integer> artistIds);

    // Eliminar lógicamente un artista por su ID
    void deleteArtistById(Integer id);

}
