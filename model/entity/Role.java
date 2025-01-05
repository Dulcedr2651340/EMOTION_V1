package com.music.Emotion.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String stageName;

    @Column(nullable = false, length = 50)
    private String type; // Tipo de artista (por ejemplo, solista, banda, dueto)

    @Column(nullable = false, length = 100)
    private String country;

    @Column(name = "debut_date")
    private LocalDate debutDate; // Fecha de debut del artista

    @Column(nullable = false)
    private Boolean status = true; // Estado del artista (activo o inactivo)

    // Relación muchos a muchos con Artist
    @ManyToMany(mappedBy = "roles")
    private Set<Artist> artists = new HashSet<>(); // Artistas que desempeñan este rol
 }
