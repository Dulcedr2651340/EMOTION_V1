package com.music.Emotion.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


/**
 * ------------------------------------------------------------------
 * Entidad Song que representa una canción en la base de datos.
 * Utiliza JPA para el mapeo de los atributos a columnas y Lombok
 * para generar código boilerplate.
 * ------------------------------------------------------------------
 */
@Entity
@Getter
@Setter
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 10)
    private String duration; // Duración de la canción en formato "mm:ss", tiene un máximo de 10 caracteres

    @Column(precision = 10, scale = 2)
    private BigDecimal rating; // Calificación de la canción, con dos dígitos de precisión y un dígito decimal

    @Column(nullable = false)
    private Boolean status = true; // Estado de la canción (activa o inactiva), por defecto es true

    // Relación muchos a muchos con Genre
    @ManyToMany
    @JoinTable(
            name = "song_genre",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();  // Géneros asociados a la canción

    // Relación con Album
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;  // La canción pertenece a un álbum específico

    // Relación muchos a muchos con Artist (inversa)
    @ManyToMany(mappedBy = "songs")
    private Set<Artist> artists = new HashSet<>();  // Artistas que interpretan la canción
}