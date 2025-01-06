package com.music.Emotion.controller;


import com.music.Emotion.model.dto.GenreRequest;
import com.music.Emotion.model.dto.GenreResponse;
import com.music.Emotion.model.dto.SongResponse;
import com.music.Emotion.service.IGenreService;
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
@RequestMapping("/api/v1/genres")
public class GenreController {

    private final IGenreService genreService;

    // Obtener todos los géneros
    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        log.info("Received request to get all genres"); //Inicializando el método
        List<GenreResponse> genres = genreService.getAllGenres();
        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} genres", genres.size()); // Finalizando para saber cuantos genres se encontró
        return ResponseEntity.ok(genres);
    }

    // Obtener un género por su ID
    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> findById(@PathVariable Integer id) {
        log.info("Received request to find genre with ID: {}", id); // Inicio del método con el id buscado
        GenreResponse genreResponse = genreService.findById(id);
        log.info("Returning genre with ID: {}", id); // Indicando que se encontró y se está retornando el género
        return ResponseEntity.ok(genreResponse);
    }

    // Guardar un nuevo género
    @PostMapping
    public ResponseEntity<GenreResponse> saveGenre(@Valid @RequestBody GenreRequest genreRequest) {
        log.info("Received request to save new genre: {}", genreRequest); // Inicializando el método
        GenreResponse genreResponse = genreService.saveGenre(genreRequest);
        log.info("Successfully saved new genre with ID: {}", genreResponse.getId()); // Finalizando tras la ejecución exitosa
        return ResponseEntity
                .created(URI.create("/api/v1/genres/" + genreResponse.getId()))
                .body(genreResponse);
    }

    // Actualizar un género existente
    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable Integer id,
            @Valid @RequestBody GenreRequest genreRequest) {
        log.info("Received request to update genre with ID: {}, Data: {}", id, genreRequest); // Inicializando el método
        GenreResponse genreResponse = genreService.updateGenre(id, genreRequest);
        log.info("Successfully updated genre with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.ok(genreResponse);
    }

    // Eliminar un género por su ID (lógicamente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable Integer id) {
        log.info("Received request to delete genre with ID: {}", id); // Inicializando el método
        genreService.deleteGenreById(id);
        log.info("Successfully deleted genre with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.noContent().build();
    }
}