package com.music.Emotion.model.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumRequest {

    @NotEmpty(message = "The field title cannot be empty or null.")
    private String title;

    @NotNull(message = "The field releaseDate cannot be null.")
    private LocalDate releaseDate;

    @NotNull(message = "The field description cannot be null.")
    private String description;

    @NotEmpty(message = "The field genreIds cannot be empty or null.")
    private Set<Integer> genreIds;  // Lista de IDs de géneros asociados con el album

    @NotEmpty(message = "The field songIds cannot be empty or null.")
    private Set<Integer> songIds;  // Lista de IDs de canciones asociados con el album
}
