package com.music.Emotion.controller;


import com.music.Emotion.model.dto.ArtistRequest;
import com.music.Emotion.model.dto.ArtistResponse;
import com.music.Emotion.service.IArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final IArtistService artistService;

    // Obtener todos los artistas
    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        log.info("Received request to get all artists"); //Inicializando el método
        List<ArtistResponse> artists = artistService.getAllArtists();
        if (artists.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        log.info("Returning {} artists", artists.size()); // Finalizando para saber cuantos genres se encontró
        return ResponseEntity.ok(artists);
    }

    // Obtener un artista por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findArtistById(@PathVariable Integer id) {
        log.info("Received request to find artist with ID: {}", id); // Inicio del método con el id buscado
        ArtistResponse artistResponse = artistService.findById(id);
        log.info("Returning artist with ID: {}", id); // Indicando que se encontró y se está retornando el artist
        return ResponseEntity.ok(artistResponse);
    }

    // Guardar un nuevo artista
    @PostMapping
    public ResponseEntity<ArtistResponse> saveArtist(@Valid @RequestBody ArtistRequest artistRequest) {
        log.info("Received request to save new artist: {}", artistRequest); // Inicializando el método
        ArtistResponse artistResponse = artistService.saveArtist(artistRequest);
        log.info("Successfully saved artist with ID: {}", artistResponse.getId());
        return ResponseEntity
                .created(URI.create("/api/v1/artists/" + artistResponse.getId())) // Finalizando tras la ejecución exitosa
                .body(artistResponse);
    }

    // Actualizar un artista existente
    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> updateArtist(
            @PathVariable Integer id,
            @Valid @RequestBody ArtistRequest artistRequest) {
        log.info("Received request to update artist with ID: {}", id);
        ArtistResponse artistResponse = artistService.updateArtist(id, artistRequest); // Inicializando el método
        log.info("Successfully updated artist with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.ok(artistResponse);
    }

    // Eliminar un artista por su ID (lógicamente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable Integer id) {
        log.info("Received request to delete artist with ID: {}", id); // Inicializando el método
        artistService.deleteArtistById(id);
        log.info("Successfully deleted artist with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.noContent().build();
    }
}



