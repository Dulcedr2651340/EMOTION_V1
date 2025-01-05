package com.music.Emotion.service.impl;

import com.music.Emotion.exception.ArtistNotFoundException;
import com.music.Emotion.mapper.ArtistMapper;
import com.music.Emotion.model.dto.ArtistRequest;
import com.music.Emotion.model.dto.ArtistResponse;
import com.music.Emotion.model.entity.*;
import com.music.Emotion.repository.*;
import com.music.Emotion.service.IArtistService;
import com.music.Emotion.service.relationship.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IArtistServiceImpl implements IArtistService {

    // Inyectamos el repositorio como una dependencia inmutable
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    private final SongGenreService songGenreService;
    private final ArtistGenreService artistGenreService;
    private final ArtistAlbumService artistAlbumService;
    private final ArtistRoleService artistRoleService;
    private final ArtistSongService artistSongService;


    @Override
    public List<ArtistResponse> getAllArtists() {
        log.info("Fetching all artists"); // Inicializando el método
        return artistRepository.findAll()
                .stream()
                .filter(artist -> artist.getStatus()) //Filtrar solo los artistas activos
                .map(artistMapper::toArtistResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponse saveArtist(ArtistRequest artistRequest) {
        log.info("Saving artist: {}", artistRequest); // Indicando que se está intentando guardar un artist;

        Artist artist = artistMapper.toEntity(artistRequest);
        log.debug("Mapped Artist entity: {}", artist); // Registro del artist mapeado desde ArtistRequest

        Set<Genre> genres = artistGenreService.getGenresByIds(artistRequest.getGenreIds());
        log.debug("Retrieved genres for artist: {}", genres); // Géneros recuperados por sus IDs

        Set<Album> albums = artistAlbumService.getAlbumByIds(artistRequest.getAlbumIds());
        log.debug("Retrieved albums for artist: {}", albums); // Album recuperados por sus IDs

        Set<Role>  roles  = artistRoleService.getRolesByIds(artistRequest.getRoleIds());
        log.debug("Retrieved role for artist: {}", roles); // roles recuperados por sus IDs

        Set<Song>  songs  = artistSongService.getSongsByIds(artistRequest.getSongIds());
        log.debug("Retrieved songs for artist: {}", songs); // Song recuperados por sus IDs

        artist.setGenres(genres);
        artist.setAlbums(albums);
        artist.setRoles(roles);
        artist.setSongs(songs);

        Artist savedArtist = artistRepository.save(artist);
        log.info("Artist saved successfully with ID: {}", savedArtist.getId()); // Indicando que el artist ha sido guardado exitosamente con su ID

        return artistMapper.toArtistResponse(savedArtist);

    }

    @Override
    public ArtistResponse updateArtist(Integer id, ArtistRequest artistRequest) {
        log.info("Attempting to artist with ID: {}", id); // Indicando que se está intentando actualizar un artista con un ID específico
        return artistRepository.findById(id)
                .map(existingArtist -> {
                    log.info("Artist found with ID: {}. Updating details...", id); // Indicando que el artist fue encontrado

                    // Actualizar los campos de la entidad Artist con los datos de artistRequest
                    existingArtist.setName(artistRequest.getName());
                    existingArtist.setStageName(artistRequest.getStageName());
                    existingArtist.setType(artistRequest.getType());
                    existingArtist.setCountry(artistRequest.getCountry());
                    existingArtist.setDebutDate(artistRequest.getDebutDate());

                    // Obtener los artistas usando el repositorio de artista y asignarlas al género
                    Set<Genre> genres = new HashSet<>(genreRepository.findAllById(artistRequest.getGenreIds()));
                    existingArtist.setGenres(genres);
                    log.debug("Associated artist with genre: {}", genres); // Información sobre las canciones asociadas

                    // Obtener los artistas usando el repositorio de artista y asignarlas al album
                    Set<Album> albums = new HashSet<>(albumRepository.findAllById(artistRequest.getAlbumIds()));
                    existingArtist.setAlbums(albums);
                    log.debug("Associated artist with Album: {}", genres); // Información sobre las canciones asociadas

                    // Obtener los artistas usando el repositorio de artista y asignarlas al role
                    Set<Role> roles = new HashSet<>(roleRepository.findAllById(artistRequest.getRoleIds()));
                    existingArtist.setRoles(roles);
                    log.debug("Associated artist with Role: {}", genres); // Información sobre las canciones asociadas

                    // Obtener los artistas usando el repositorio de artista y asignarlas al song
                    Set<Song> songs = new HashSet<>(songRepository.findAllById(artistRequest.getSongIds()));
                    existingArtist.setSongs(songs);
                    log.debug("Associated artist with song: {}", songs); // Información sobre las canciones asociadas

                    // Guardar el artist actualizado en la base de datos
                    Artist updatedArtist = artistRepository.save(existingArtist);
                    log.info("Artist with ID: {} updated successfully", id); // Indicando que la actualización fue exitosa

                    return updatedArtist;
                })
               .map(artistMapper::toArtistResponse)
                .orElseThrow(() -> {
                    log.error("Artist with ID: {} not found", id); // Indicando que no se encontró el artist con el ID especificado
                    return new ArtistNotFoundException("Artist with ID " + id + " not found");
                });
    }

    @Override
    public ArtistResponse findById(Integer id) {
        log.info("Finding artist with ID: {}", id); // Indicando que se está buscando un artist con un ID específico
        return artistRepository.findById(id)
                .map(artist -> {
                    log.debug("Found artist entity: {}", artist); // Album encontrado antes de mapearlo a AlbumResponse
                    return artistMapper.toArtistResponse(artist);
                })
                .orElseThrow(() -> {
                    log.error("Artist with ID: {} not found", id); // Error si el album no se encuentra
                    return new ArtistNotFoundException("Artist with ID " + id + " not found");
                });
    }


    @Override
    public Set<Artist> getArtistsByIds(Set<Integer> artistIds) {
        // Validación inicial para evitar problemas con IDs nulos o vacíos
        if (artistIds == null || artistIds.isEmpty()) {
            log.warn("No artist IDs provided.");
            return Collections.emptySet();
        }

        log.info("Fetching artists with IDs: {}", artistIds);

        // Recupera los artistas desde el repositorio
        Set<Artist> artists = new HashSet<>(artistRepository.findAllById(artistIds));

        // Verifica si no se encontraron artistas y lanza una excepción
        if (artists.isEmpty()) {
            log.error("No artists found for provided IDs: {}", artistIds);
            throw new ArtistNotFoundException("No artists found for provided IDs: " + artistIds);
        }

        log.info("Successfully fetched {} artists.", artists.size());
        return artists;
    }

    @Override
    public void deleteArtistById(Integer id) {
        log.info("Attempting to delete artist with ID: {}", id); // Log indicando el inicio del método

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist with ID " + id + " not found"));

        // Cambiar el estado del artist a false (inactivo)
        artist.setStatus(false);
        artistRepository.save(artist); // Guardar los cambios en la base de datos

        log.info("Successfully marked artist with ID: {} as inactive", id); // Confirmación de que el artist fue marcado como inactivo
    }
}
