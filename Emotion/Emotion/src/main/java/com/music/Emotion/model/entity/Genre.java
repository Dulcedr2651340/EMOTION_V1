package com.music.Emotion.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 2000) // Define un límite de caracteres para la descripción
    private String description;

    @Column(nullable = false)
    private Boolean status = true; // Estado del género (activo o inactivo), por defecto es true

    // Relación muchos a muchos con Song
    @ManyToMany(mappedBy = "genres")
    private Set<Song> songs = new HashSet<>();

    // Relación muchos a muchos con Album
    @ManyToMany(mappedBy = "genres")
    private Set<Album> albums = new HashSet<>();

    // Relación muchos a muchos con Artist
    @ManyToMany(mappedBy = "genres")
    private Set<Artist> artists = new HashSet<>();

}