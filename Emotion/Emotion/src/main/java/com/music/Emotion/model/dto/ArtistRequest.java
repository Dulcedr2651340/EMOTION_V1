package com.music.Emotion.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArtistRequest {

    @NotEmpty(message = "The field name cannot be empty or null.")
    private String name;

    @NotNull(message = "The field stageName cannot be null.")
    private String stageName;

    @NotNull(message = "The field type cannot be null.")
    private String type;

    @NotNull(message = "The field country cannot be null.")
    private String country;

    @NotNull(message = "The field LocalDate cannot be null.")
    private LocalDate debutDate;

    @NotEmpty(message = "The field genreIds cannot be empty or null.")
    private Set<Integer> genreIds;  // Lista de IDs de géneros asociados con el artista

    @NotEmpty(message = "The field roleIds cannot be empty or null.")
    private Set<Integer> roleIds;  // Lista de IDs de roles asociados con el artista

    @NotEmpty(message = "The field songIds cannot be empty or null.")
    private Set<Integer> songIds;  // Lista de IDs de canciones asociadas con el artista

    @NotEmpty(message = "The field albumIds cannot be empty or null.")
    private Set<Integer> albumIds;  // Lista de IDs de álbumes asociados con el artista
}
