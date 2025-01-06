package com.music.Emotion.controller;


import com.music.Emotion.model.dto.AlbumRequest;
import com.music.Emotion.model.dto.AlbumResponse;
import com.music.Emotion.service.IAlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final IAlbumService albumService;

    // Obtener todos los albums
    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        log.info("Received request to get all albums"); //Inicializando el método
        List<AlbumResponse> albums = albumService.getAllAlbums();
        if (albums.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} albums", albums.size()); // Finalizando para saber cuantos albums se encontró
        return ResponseEntity.ok(albums);
    }

    // Obtener un album por su ID
    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> findById(@PathVariable Integer id) {
        log.info("Received request to find album with ID: {}", id); // Inicio del método con el id buscado
        AlbumResponse albumResponse = albumService.findById(id);
        log.info("Returning album with ID: {}", id); // Indicando que se encontró y se está retornando el album
        return ResponseEntity.ok(albumResponse);
    }

    // Guardar un nuevo album
    @PostMapping
    public ResponseEntity<AlbumResponse> saveAlbum(@Valid @RequestBody AlbumRequest albumRequest) {
        log.info("Received request to save new album: {}", albumRequest); // Inicializando el método
        AlbumResponse albumResponse = albumService.saveAlbum(albumRequest);
        log.info("Successfully saved new album with ID: {}", albumResponse.getId()); // Finalizando tras la ejecución exitosa
        return ResponseEntity
                .created(URI.create("/api/v1/albums/" + albumResponse.getId()))
                .body(albumResponse);
    }

    // Actualizar un album existente
    @PutMapping("/{id}")

    public ResponseEntity<AlbumResponse> updateAlbum(
            @PathVariable Integer id,
            @Valid @RequestBody AlbumRequest albumRequest) {
        log.info("Received request to update album with ID: {}, Data: {}", id, albumRequest); // Inicializando el método
        AlbumResponse albumResponse = albumService.updateAlbum(id, albumRequest);
        log.info("Successfully updated album with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.ok(albumResponse);
    }

    // Eliminar un album por su ID (lógicamente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable Integer id) {
        log.info("Received request to delete album with ID: {}", id); // Inicializando el método
        albumService.deleteAlbumById(id);
        log.info("Successfully deleted album with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.noContent().build();
    }
}
