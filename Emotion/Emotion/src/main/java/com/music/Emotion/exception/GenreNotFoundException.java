package com.music.Emotion.exception;

import org.springframework.validation.BindingResult;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String message) {
        super(message);
    }

}
