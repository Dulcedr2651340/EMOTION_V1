package com.music.Emotion.service.impl;

import com.music.Emotion.exception.GenreNotFoundException;
import com.music.Emotion.mapper.GenreMapper;
import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import com.music.Emotion.repository.GenreRepository;
import com.music.Emotion.repository.SongRepository;
import com.music.Emotion.service.IGenreService;
import com.music.Emotion.service.relationship.SongGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IGenreServiceImpl implements IGenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    private final SongGenreService songGenreService; // Reemplaza la dependencia circular
    private final SongRepository songRepository;

    @Override
    public List<GenreResponse> getAllGenres() {
        log.info("Fetching all genres"); // Inicializando el método
        return genreRepository.findAll()
                .stream()
                .filter(genre -> genre.getStatus()) // Filtrar solo los géneros que están activos (status = true)
                .map(genreMapper::toGenreResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GenreResponse saveGenre(GenreRequest genreRequest) {
        log.info("Saving genre: {}", genreRequest); // Indicando que se está intentando guardar un género

        Genre genre = genreMapper.toEntity(genreRequest);
        log.debug("Mapped Genre entity: {}", genre); // Registro del género mapeado desde GenreRequest

        Set<Song> songs = songGenreService.getSongsByIds(genreRequest.getSongIds());
        log.debug("Retrieved songs for genre: {}", songs); // Los géneros recuperados por sus IDs

        genre.setSongs(songs);

        Genre savedGenre = genreRepository.save(genre);
        log.info("Genre saved successfully with ID: {}", savedGenre.getId()); // Indicando que el género ha sido guardado exitosamente con su ID

        return genreMapper.toGenreResponse(savedGenre);

    }

    @Override
    public GenreResponse updateGenre(Integer id, GenreRequest genreRequest) {
        log.info("Attempting to update genre with ID: {}", id); // Indicando que se está intentando actualizar un género con un ID específico

        return genreRepository.findById(id)
                .map(existingGenre -> {
                    log.info("Genre found with ID: {}. Updating details...", id); // Indicando que el género fue encontrado

                    // Actualizar los campos de la entidad Genre con los datos de genreRequest
                    existingGenre.setName(genreRequest.getName());
                    existingGenre.setDescription(genreRequest.getDescription());
                    log.debug("Updated genre fields with new name: {} and description: {}", genreRequest.getName(), genreRequest.getDescription()); // Información detallada sobre los campos actualizados

                    // Obtener las canciones usando el repositorio de canciones y asignarlas al género
                    Set<Song> songs = new HashSet<>(songRepository.findAllById(genreRequest.getSongIds()));
                    existingGenre.setSongs(songs);
                    log.debug("Associated songs with genre: {}", songs); // Información sobre las canciones asociadas

                    // Guardar el género actualizado en la base de datos
                    Genre updatedGenre = genreRepository.save(existingGenre);
                    log.info("Genre with ID: {} updated successfully", id); // Indicando que la actualización fue exitosa

                    return updatedGenre;
                })
                .map(genreMapper::toGenreResponse)
                .orElseThrow(() -> {
                    log.error("Genre with ID: {} not found", id); // Indicando que no se encontró el género con el ID especificado
                    return new GenreNotFoundException("Genre with ID " + id + " not found");
                });
    }

    @Override
    public GenreResponse findById(Integer id) {
        log.info("Finding genre with ID: {}", id); // Indicando que se está buscando un género con un ID específico

        return genreRepository.findById(id)
                .map(genre -> {
                    log.debug("Found genre entity: {}", genre); // Género encontrado antes de mapearlo a GenreResponse
                    return genreMapper.toGenreResponse(genre);
                })
                .orElseThrow(() -> {
                    log.error("Genre with ID: {} not found", id); // Error si el género no se encuentra
                    return new GenreNotFoundException("Genre with ID " + id + " not found");
                });
    }

    @Override
    public Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        log.info("Fetching genres with IDs: {}", genreIds); // Indicando que se está intentando obtener los géneros con los IDs proporcionados

        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(genreIds));

        if (genres.isEmpty()) {
            log.warn("No genres found for provided IDs: {}", genreIds); // Si no se encuentran géneros para los IDs dados
        } else {
            log.debug("Found genres: {}", genres); // Los géneros encontrados para los IDs proporcionados
        }

        return genres;
    }

    @Override
    public void deleteGenreById(Integer id) {
        log.info("Attempting to mark genre with ID: {} as inactive", id); // Indicando que se está intentando marcar el género como inactivo

        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre with ID " + id + " not found"));

        log.debug("Genre found: {}", genre); // Confirmación de que el género fue encontrado

        // Cambiar el estado del género a inactivo
        genre.setStatus(false);
        genreRepository.save(genre);

        log.info("Successfully marked genre with ID: {} as inactive", id); // Confirmación de que el género fue marcado como inactivo
    }
}
