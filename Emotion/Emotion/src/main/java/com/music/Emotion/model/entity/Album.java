package com.music.Emotion.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Boolean status = true; // Estado del álbum (activo/inactivo), por defecto es true

    // Relación uno a muchos con Song
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs = new ArrayList<>();

    // Relación muchos a muchos con Genre
    @ManyToMany
    @JoinTable(
            name = "album_genre",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>(); // Géneros asociados al álbum

    // Relación muchos a muchos con Artist
    @ManyToMany
    @JoinTable(
            name = "album_artist",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists = new HashSet<>(); // Artistas asociados al álbum

    // Relación muchos a muchos con Role
    @ManyToMany
    @JoinTable(
            name = "album_role",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();  // Roles asociados al álbum (por ejemplo, "Productor", "Músico de estudio").
}

