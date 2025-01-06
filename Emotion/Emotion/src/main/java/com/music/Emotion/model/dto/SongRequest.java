package com.music.Emotion.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongRequest {

    @NotEmpty(message = "The field name cannot be empty or null.")
    private String name;

    @NotNull(message = "The field duration cannot be null.")
    private String  duration;

    @NotNull(message = "The field rating cannot be null.")
    private BigDecimal rating;

    @NotEmpty(message = "The field genreIds cannot be empty or null.")
    private Set<Integer> genreIds; // Lista de IDs de géneros asociados con la canción

}
