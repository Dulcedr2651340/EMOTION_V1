package com.music.Emotion.service.relationship;

import com.music.Emotion.model.entity.Album;
import com.music.Emotion.model.entity.Artist;
import com.music.Emotion.model.entity.Genre;
import com.music.Emotion.model.entity.Role;
import com.music.Emotion.repository.ArtistRepository;
import com.music.Emotion.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArtistRoleService {

    private final RoleRepository roleRepository;
    private final ArtistRepository artistRepository;

    public Set<Role> getRolesByIds(Set<Integer> roleIds) {
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }
    public Set<Artist> getArtistByIds(Set<Integer> artistIds) {
        return new HashSet<>(artistRepository.findAllById(artistIds));
    }
}
