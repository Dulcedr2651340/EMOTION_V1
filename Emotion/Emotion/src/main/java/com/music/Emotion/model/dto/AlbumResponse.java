package com.music.Emotion.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class AlbumResponse {

    private Integer id;
    private String title;
    private LocalDate releaseDate;
    private String description;
    private String status;
    private List<Integer> genreIds;
    private List<Integer> songIds;
}
