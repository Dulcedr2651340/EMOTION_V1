package com.music.Emotion.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SongResponse {

    private Integer id;
    private String  name;
    private String  duration;
    private Double  rating;
    private GenreResponse genre;
    private String status;
}
