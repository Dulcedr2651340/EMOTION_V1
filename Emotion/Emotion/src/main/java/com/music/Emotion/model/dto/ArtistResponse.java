package com.music.Emotion.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ArtistResponse {

    //Esto se maneja mediante la implementación, no es para mostrar

    private Integer id;
    private String name;
    private String stageName;
    private String type;
    private String country;
    private LocalDate debutDate;
    private String status;
}
