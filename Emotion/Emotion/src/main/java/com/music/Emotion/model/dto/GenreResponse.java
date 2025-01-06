package com.music.Emotion.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreResponse {

    //Esto se maneja mediante la implementación, no es para mostrar

    private Integer id;
    private String name;
    private String description;

}
