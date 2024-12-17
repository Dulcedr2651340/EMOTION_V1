package com.music.Emotion.utils;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SONG_NOT_FOUND("ERR_SONG_001", "Song not found."),
    INVALID_SONG("ERR_SONG_002", "Invalid song parameters."),

    GENRE_NOT_FOUND("ERR_GENRE_001", "Genre not found."),
    INVALID_GENRE("ERR_GENRE_002", "Invalid song parameters."),

    ALBUM_NOT_FOUND("ERR_ALBUM_001", "Album not found."),
    INVALID_ALBUM("ERR_ALBUM_002", "Invalid song parameters."),

    ARTIST_NOT_FOUND("ERR_ARTIST_001", "Artist not found."),
    INVALID_ARTIST("ERR_ARTIST_002", "Invalid Artist parameters."),

    ROLE_NOT_FOUND("ERR_ROLE_001", "Role not found."),
    INVALID_ROLE("ERR_ROLE_002", "Invalid Role parameters."),

    GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
