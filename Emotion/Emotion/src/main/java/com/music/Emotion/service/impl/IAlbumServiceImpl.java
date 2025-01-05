package com.music.Emotion.service.impl;

import com.music.Emotion.exception.AlbumNotFoundException;
import com.music.Emotion.exception.GenreNotFoundException;
import com.music.Emotion.mapper.AlbumMapper;
import com.music.Emotion.model.dto.AlbumRequest;
import com.music.Emotion.model.dto.AlbumResponse;
import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import com.music.Emotion.repository.AlbumRepository;
import com.music.Emotion.repository.GenreRepository;
import com.music.Emotion.repository.SongRepository;
import com.music.Emotion.service.IAlbumService;
import com.music.Emotion.service.relationship.AlbumGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IAlbumServiceImpl implements IAlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final AlbumGenreService albumGenreService;
    private final GenreRepository genreRepository;
    private final SongRepository songRepository;

    @Override
    public List<AlbumResponse> getAllAlbums() {
        log.info("Fetching all albums"); // Inicializando el método
        return albumRepository.findAll()
                .stream()
                .filter(album -> album.getStatus()) // Filtrar solo las albums activas
                .map(albumMapper::toAlbumResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AlbumResponse saveAlbum(AlbumRequest albumRequest) {
        log.info("Saving album: {}", albumRequest); // Indicando que se está intentando guardar un álbum

        Album album = albumMapper.toEntity(albumRequest);
        log.debug("Mapped Album entity: {}", album); // Registro del álbum mapeado desde AlbumRequest

        Set<Genre> genres = albumGenreService.getGenresByIds(albumRequest.getGenreIds());
        log.debug("Retrieved genres for album: {}", genres); // Géneros recuperados por sus IDs

        album.setGenres(genres);

        Album savedAlbum = albumRepository.save(album);
        log.info("Album saved successfully with ID: {}", savedAlbum.getId()); // Indicando que el álbum ha sido guardado exitosamente con su ID

        return albumMapper.toAlbumResponse(savedAlbum);
    }

    @Override
    public AlbumResponse updateAlbum(Integer id, AlbumRequest albumRequest) {
        log.info("Attempting to update album with ID: {}", id);

        return albumRepository.findById(id)
                .map(existingAlbum -> {
                    log.info("Album found with ID: {}. Updating details...", id);

                    // Actualizar campos básicos
                    existingAlbum.setTitle(albumRequest.getTitle());
                    existingAlbum.setReleaseDate(albumRequest.getReleaseDate());
                    existingAlbum.setDescription(albumRequest.getDescription());
                    log.debug("Updated album fields with title: {}, release date: {}, description: {}",
         *                   albumRequest.getTitle(), albumRequest.getReleaseDate(), albumRequest.getDescription());

                    // Actualizar géneros si existen nuevos
                    if (albumRequest.getGenreIds() != null && !albumRequest.getGenreIds().isEmpty()) {
                        Set<Genre> newGenres = new HashSet<>(genreRepository.findAllById(albumRequest.getGenreIds()));
                        existingAlbum.getGenres().addAll(newGenres);
                        log.debug("Added genres: {}", newGenres);
                    }

                    // Actualizar canciones si existen nuevas
                    if (albumRequest.getSongIds() != null && !albumRequest.getSongIds().isEmpty()) {
                        List<Song> newSongs = songRepository.findAllById(albumRequest.getSongIds());

                        // Agregar nuevas canciones sin eliminar las existentes
                        newSongs.forEach(song -> {
                            song.setAlbum(existingAlbum);
                            if (!existingAlbum.getSongs().contains(song)) {
                                existingAlbum.getSongs().add(song);
                            }
                        });

                        log.debug("Added songs: {}", newSongs);
                    }

                    // Guardar el álbum actualizado
                    Album updatedAlbum = albumRepository.save(existingAlbum);
                    log.info("Album with ID: {} updated successfully", id);

                    return updatedAlbum;
                })
                .map(albumMapper::toAlbumResponse)
                .orElseThrow(() -> {
                    log.error("Album with ID: {} not found", id);
                    return new AlbumNotFoundException("Album with ID " + id + " not found");
                });
    }

    @Override
    public AlbumResponse findById(Integer id) {
        log.info("Finding album with ID: {}", id); // Indicando que se está buscando un album con un ID específico

        return albumRepository.findById(id)
                .map(album -> {
                    log.debug("Found album entity: {}", album); // Album encontrado antes de mapearlo a AlbumResponse
                    return albumMapper.toAlbumResponse(album);
                })
                .orElseThrow(() -> {
                    log.error("Album with ID: {} not found", id); // Error si el album no se encuentra
                    return new AlbumNotFoundException("Album with ID " + id + " not found");
                });
    }

    @Override
    public Set<Album> getAlbumsByIds(Set<Integer> albumIds) {
        log.info("Fetching albums wih IDs: {}", albumIds); // Indicando que se está intentando obtener los album con los IDs proporcionados

        Set<Album> albums = new HashSet<>(albumRepository.findAllById(albumIds));

        if (albums.isEmpty()) {
            log.warn("No albums found for provided IDs: {}", albumIds); // Si no se encuentran albums para los IDs dados
        } else {
            log.debug("Found genres: {}", albums); // Los albums encontrados para los IDs proporcionados
        }

        return albums;
    }


    @Override
    public Set<Genre> getGenresByIds(Set<Integer> genreIds) {
        // Validación inicial para comprobar si los IDs son válidos
        if (genreIds == null || genreIds.isEmpty()) {
            log.warn("No genre IDs provided.");
            return Collections.emptySet();
        }

        log.info("Fetching genres with IDs: {}", genreIds);

        // Buscar todos los géneros con los IDs proporcionados
        List<Genre> genresList = genreRepository.findAllById(genreIds);

        // Si no se encuentra ningún género, lanzar una excepción
        if (genresList.isEmpty()) {
            log.error("No genres found for the provided IDs: {}", genreIds);
            throw new GenreNotFoundException("No genres found for the provided IDs.");
        }

        log.info("Genres successfully found: {}", genresList.size());
        return new HashSet<>(genresList);
    }


    @Override
    public void deleteAlbumById(Integer id) {
        log.info("Attempting to delete album with ID: {}", id); // Log indicando el inicio del método

        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException("Album with ID " + id + " not found"));

        // Cambiar el estado del álbum a false (inactivo)
        album.setStatus(false);
        albumRepository.save(album); // Guardar los cambios en la base de datos

        log.info("Successfully marked album with ID: {} as inactive", id); // Confirmación de que el álbum fue marcado como inactivo
    }
}
