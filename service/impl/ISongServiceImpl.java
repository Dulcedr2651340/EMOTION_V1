package com.music.Emotion.service.impl;

import com.music.Emotion.exception.SongNotFoundException;
import com.music.Emotion.mapper.SongMapper;
import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import com.music.Emotion.repository.GenreRepository;
import com.music.Emotion.repository.SongRepository;
import com.music.Emotion.service.ISongService;
import com.music.Emotion.service.relationship.SongGenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ISongServiceImpl implements ISongService {

    // Inyectamos el repositorio como una dependencia inmutable
    private final SongRepository songRepository;
    private final GenreRepository genreRepository;
    private final SongMapper songMapper;
    private final SongGenreService songGenreService; // Reemplaza la dependencia circular

    // Obtener todas las canciones
    @Override
    public List<SongResponse> getAllSongs() {
        log.info("Fetching all songs"); // Indicando que se está intentando obtener todas las canciones

        List<Song> songs = songRepository.findAll();
        if (songs.isEmpty()) {
            log.warn("No songs found"); // Advertencia en caso de no encontrar canciones
            throw new SongNotFoundException("No songs found");
        }

        log.debug("Number of songs found: {}", songs.size()); // Registro del número de canciones encontradas

        return songs.stream()
                .filter(song -> song.getStatus()) // Filtrar solo las canciones activas
                .map(songMapper::toSongResponse)
                .collect(Collectors.toList());
    }

    // Crear una nueva cancion
    @Override
    public SongResponse saveSong(SongRequest songRequest) {
        log.info("Saving a new song: {}", songRequest); // Indicando que se está intentando guardar una nueva canción

        // Convertir SongRequest a Song entity
        Song song = songMapper.toEntity(songRequest);
        log.debug("Mapped Song entity: {}", song); // Registro del mapeo del objeto SongRequest a Song

        // Obtener los géneros asociados a la canción
        Set<Genre> genres = songGenreService.getGenresByIds(songRequest.getGenreIds());
        log.debug("Retrieved genres for song: {}", genres); // Registro de los géneros asociados recuperados

        // Asignar géneros a la canción
        song.setGenres(genres);

        // Guardar la canción y convertirla a SongResponse
        Song savedSong = songRepository.save(song);
        log.info("Song saved successfully with ID: {}", savedSong.getId()); // Indicando que la canción fue guardada correctamente

        return songMapper.toSongResponse(savedSong);
    }

    // Actualiza una cancion
    @Override
    public SongResponse updateSong(Integer id, SongRequest songRequest) {
        log.info("Attempting to update song with ID: {}", id); // Indicando que se intenta actualizar una canción con un ID específico

        return songRepository.findById(id) // 1. Buscar la canción en la base de datos por su ID.
                .map(existingSong -> {
                    log.info("Found song with ID: {}. Updating with new details.", id); // Indicar que la canción fue encontrada

                    // 2. Actualizar los campos de la entidad 'Song' con los datos de 'songRequest'.
                    existingSong.setName(songRequest.getName());
                    existingSong.setDuration(songRequest.getDuration());
                    existingSong.setRating(songRequest.getRating());
                    log.debug("Updated song fields with provided SongRequest: {}", songRequest); // Registro de los nuevos valores aplicados a la canción

                    // 3. Obtener los géneros asociados usando el repositorio y asignarlos a la canción actualizada.
                    Set<Genre> genres = new HashSet<>(genreRepository.findAllById(songRequest.getGenreIds()));
                    existingSong.setGenres(genres);
                    log.debug("Assigned genres to song: {}", genres); // Registro de los géneros asignados

                    // 4. Guardar la canción actualizada en la base de datos.
                    Song updatedSong = songRepository.save(existingSong);
                    log.info("Successfully updated song with ID: {}", id); // Indicar que la canción fue guardada correctamente

                    return updatedSong;
                })
                .map(songMapper::toSongResponse)  // 5. Convertir la entidad 'Song' actualizada a 'SongResponse' para devolver.
                .orElseThrow(() -> {
                    log.error("Song with ID: {} not found. Cannot update.", id); // Log de error si la canción no fue encontrada
                    return new SongNotFoundException("Song with ID " + id + " not found");
                });
    }

    // Listar todas las canciones
    @Override
    public List<SongResponse> findAllBySong() {
        log.info("Fetching all songs"); // Inicio del método, indicando que se está intentando obtener todas las canciones

        List<Song> songs = songRepository.findAll();
        if (songs.isEmpty()) {
            log.warn("No songs found"); // Advertencia si no se encuentran canciones
        } else {
            log.info("Found {} songs", songs.size()); // Indica cuántas canciones fueron encontradas
        }

        return songs.stream()
                .map(songMapper::toSongResponse)
                .collect(Collectors.toList());
    }

    // Buscar por Id

    @Override
    public SongResponse findById(Integer id) {
        log.info("Fetching song with ID: {}", id); // Inicio del método, indicando que se está intentando obtener una canción con un ID específico

        return songRepository.findById(id)
                .map(song -> {
                    log.info("Song found with ID: {}", id); // Indica que se encontró la canción
                    return songMapper.toSongResponse(song);
                })
                .orElseThrow(() -> {
                    log.error("Song with ID {} not found", id); // Error si no se encuentra la canción
                    return new SongNotFoundException("Song with ID " + id + " not found");
                });
    }

    @Override
    public Set<Song> getSongsByIds(Set<Integer> songIds) {
        log.info("Fetching songs with IDs: {}", songIds); // Indicando que se está intentando obtener un conjunto de canciones con IDs específicos

        Set<Song> songs = new HashSet<>(songRepository.findAllById(songIds));
        if (songs.isEmpty()) {
            log.warn("No songs found for the provided IDs: {}", songIds); // Advertencia si no se encuentra ninguna canción
        } else {
            log.info("Fetched {} songs for the provided IDs", songs.size()); // Informa cuántas canciones se encontraron
        }

        return songs;
    }

    // Eliminar por Id
    @Override
    public void deleteSongById(Integer id) {
        log.info("Attempting to mark song with ID: {} as inactive", id); // Indicando que se intentará marcar una canción como inactiva

        Song song = songRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Song with ID: {} not found, cannot mark as inactive.", id); // Error si no se encuentra la canción
                    return new SongNotFoundException("Song not found with ID: " + id);
                });

        log.info("Song with ID: {} found, proceeding to mark as inactive.", id); // Indica que la canción fue encontrada y procederá a ser marcada como inactiva
        song.setStatus(false); // Cambiar el estado de la canción a inactivo
        songRepository.save(song); // Guardar el cambio en la base de datos
        log.info("Successfully marked song with ID: {} as inactive", id); // Indica que la canción fue marcada como inactiva exitosamente
    }
}