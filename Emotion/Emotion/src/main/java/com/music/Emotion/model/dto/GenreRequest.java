package com.music.Emotion.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Song;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // No se muestre la respuesta, se omita
public class GenreRequest {

    @NotEmpty(message = "The field name cannot be empty or null.")
    private String name;

    @NotNull(message = "The field description cannot be null.")
    private String description;

    @NotNull(message = "The field songIds cannot be empty or null.")
    private Set<Integer> songIds;  // Lista de IDs de canciones asociadas con el género

}
