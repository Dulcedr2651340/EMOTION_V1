package com.music.Emotion.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequest {

    @NotEmpty(message = "The field name cannot be empty or null.")
    private String name;

    @NotEmpty(message = "The field stageName cannot be empty or null.")
    private String stageName;

    @NotEmpty(message = "The field type cannot be empty or null.")
    private String type;

    @NotEmpty(message = "The field country cannot be empty or null.")
    private String country;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate debutDate;

    @NotEmpty(message = "The field genreIds cannot be empty or null.")
    private Set<Integer> artistIds;  // Lista de IDs de artistas asociados con el rol
}
