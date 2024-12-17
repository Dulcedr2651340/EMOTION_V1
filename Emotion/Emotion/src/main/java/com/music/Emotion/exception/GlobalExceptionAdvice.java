package com.music.Emotion.exception;

import com.music.Emotion.utils.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import static com.music.Emotion.utils.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SongNotFoundException.class)
    public ErrorResponse handlerSongNotFoundException(SongNotFoundException ex){

        // Capturando el mensaje de error de SongNotFoundException
        log.error("SongNotFoundException handled: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.SONG_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorCode.SONG_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenreNotFoundException.class)
    public ErrorResponse handlerGenreNotFoundException(GenreNotFoundException ex){

        // Capturando el mensaje de error de GenreNotFoundException
        log.error("GenreNotFoundException handled: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.GENRE_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorCode.GENRE_NOT_FOUND.getMessage()) // Usa el mensaje que viene de la excepción
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AlbumNotFoundException.class)
    public ErrorResponse handlerAlbumNotFoundException(AlbumNotFoundException ex){

        // Capturando el mensaje de error de AlbumNotFoundException
        log.error("AlbumNotFoundException handled: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.ALBUM_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorCode.ALBUM_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ArtistNotFoundException.class)
    public ErrorResponse handlerArtistNotFoundException(ArtistNotFoundException ex){

        // Capturando el mensaje de error de ArtistNotFoundException
        log.error("ArtistNotFoundException handled: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.ARTIST_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorCode.ARTIST_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ErrorResponse handlerRoleNotFoundException(RoleNotFoundException ex){

        // Capturando el mensaje de error de RoleNotFoundException
        log.error("RoleNotFoundException handled: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.ROLE_NOT_FOUND.getCode())
                .status(HttpStatus.NOT_FOUND)
                .message(ErrorCode.ROLE_NOT_FOUND.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex){

        BindingResult result = ex.getBindingResult();

        // Capturando el mensaje de error de MethodArgumentNotValidException
        log.error("Validation error occurred: {}", result.getFieldErrors());

        String entity = result.getObjectName();

        ErrorCode errorCode;
        if ("genreRequest".equalsIgnoreCase(entity)) {
            errorCode = ErrorCode.INVALID_GENRE;
        } else if ("songRequest".equalsIgnoreCase(entity)) {
            errorCode = ErrorCode.INVALID_SONG;
        } else if ("albumRequest".equalsIgnoreCase(entity)) {
            errorCode = ErrorCode.INVALID_ALBUM;
        } else if ("artistRequest".equalsIgnoreCase(entity)) {
                errorCode = ErrorCode.INVALID_ARTIST;
        } else if ("roleRequest".equalsIgnoreCase(entity)) {
            errorCode = ErrorCode.INVALID_ROLE;
        } else {
            errorCode = ErrorCode.GENERIC_ERROR;
        }

        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(HttpStatus.BAD_REQUEST)
                .message(errorCode.getMessage())
                .detailMessages(result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGeneralErrors(Exception ex) { // Renombrar método
        if (ex.getMessage().contains("No endpoint GET")) {
            log.warn("Static file not found: {}", ex.getMessage());
            return null; // Ignorar errores de archivos estáticos
        }

        log.error("Unexpected error occurred: {}", ex.getMessage());
        return ErrorResponse.builder()
                .code(ErrorCode.GENERIC_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorCode.GENERIC_ERROR.getMessage())
                .detailMessages(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }
   /* @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleInternalServeError(Exception ex) {

        // Capturando el mensaje de error de Exception
        log.error("Unexpected error occurred: {}", ex.getMessage());

        return ErrorResponse.builder()
                .code(ErrorCode.GENERIC_ERROR.getCode())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ErrorCode.GENERIC_ERROR.getMessage())
                .detailMessages(Collections.singletonList(ex.getMessage()))
                .timestamp(LocalDateTime.now())
                .build();
    }*/


}
