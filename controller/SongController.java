package com.music.Emotion.controller;


import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.dto.SongRequest;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.service.ISongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/songs")
public class SongController {

    private final ISongService songService;

    // Obtener todas las canciones
    @GetMapping
    public ResponseEntity<List<SongResponse>> getAllSongs() {
        log.info("Received request to get all songs"); //Inicializando el método
        List<SongResponse> songs = songService.getAllSongs();
        if (songs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} songs", songs.size()); // Finalizando para saber cuantos songs se encontró
        return ResponseEntity.ok(songs);
    }

    // Obtener una canción por su ID
    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Integer id){
        log.info("Received request to find song with ID: {}", id); // Inicio del método con el id buscado
        SongResponse songResponse = songService.findById(id);
        log.info("Returning song with ID: {}", id); // Indicando que se encontró y se está retornando el song
        return ResponseEntity.ok(songResponse);
    }

    // Guardar una nueva canción
    @PostMapping
    public ResponseEntity<SongResponse> saveSong(@Valid @RequestBody SongRequest songRequest){
        log.info("Received request to save new song: {}", songRequest); // Inicializando el método
        SongResponse songResponse = songService.saveSong(songRequest);
        log.info("Successfully saved new song with ID: {}", songResponse.getId()); // Finalizando tras la ejecución exitosa
        return ResponseEntity
                .created(URI.create("/api/v1/songs/" + songResponse.getId()))
                .body(songResponse);
    }

    // Actualizar una canción existente
    @PutMapping("/{id}")
    public ResponseEntity<SongResponse> updateSong(
            @PathVariable Integer id,
            @Valid @RequestBody SongRequest songRequest){
        log.info("Received request to update song with ID: {}, Data: {}", id, songRequest); // Inicializando el método
        SongResponse songResponse = songService.updateSong(id, songRequest);
        log.info("Successfully updated song with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.ok(songResponse);
    }

    // Eliminar una canción por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSongId(@PathVariable Integer id){
        log.info("Received request to delete song with ID: {}", id); // Inicializando el método
        songService.deleteSongById(id);
        log.info("Successfully deleted song with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.noContent().build();
    }
}
