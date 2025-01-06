package com.music.Emotion.service.impl;

import com.music.Emotion.exception.RoleNotFoundException;
import com.music.Emotion.mapper.RoleMapper;
import com.music.Emotion.model.dto.RoleRequest;
import com.music.Emotion.model.dto.RoleResponse;
import com.music.Emotion.model.entity.Role;
import com.music.Emotion.repository.RoleRepository;
import com.music.Emotion.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class IRoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        log.info("Fetching all roles");
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            log.warn("No roles found.");
            return Collections.emptyList();
        }

        log.info("Returning {} roles.", roles.size());
        return roles.stream()
                .filter(Role::getStatus) // Filtrar roles activos
                .map(roleMapper::toRoleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse saveRole(RoleRequest roleRequest) {
        log.info("Saving role: {}", roleRequest);
        Role role = roleMapper.toEntity(roleRequest);
        log.debug("Mapped Role entity: {}", role);

        Role savedRole = roleRepository.save(role);
        log.info("Role saved successfully with ID: {}", savedRole.getId());

        return roleMapper.toRoleResponse(savedRole);
    }

    @Override
    public RoleResponse updateRole(Integer id, RoleRequest roleRequest) {
        log.info("Attempting to update role with ID: {}", id);
        return roleRepository.findById(id)
                .map(existingRole -> {
                    log.info("Role found with ID: {}. Updating details...", id);

                    // Actualizar los campos
                    existingRole.setName(roleRequest.getName());
                    existingRole.setStageName(roleRequest.getStageName());
                    existingRole.setType(roleRequest.getType());
                    existingRole.setCountry(roleRequest.getCountry());
                    existingRole.setDebutDate(roleRequest.getDebutDate());
                    log.debug("Updated role fields with request data: {}", roleRequest);

                    // Guardar el rol actualizado
                    Role updatedRole = roleRepository.save(existingRole);
                    log.info("Role with ID: {} updated successfully.", id);

                    return updatedRole;
                })
                .map(roleMapper::toRoleResponse)
                .orElseThrow(() -> {
                    log.error("Role with ID: {} not found. Cannot update.", id);
                    return new RoleNotFoundException("Role with ID " + id + " not found.");
                });
    }

    @Override
    public RoleResponse findById(Integer id) {
        log.info("Fetching role with ID: {}", id);
        return roleRepository.findById(id)
                .map(role -> {
                    log.debug("Found role entity: {}", role);
                    return roleMapper.toRoleResponse(role);
                })
                .orElseThrow(() -> {
                    log.error("Role with ID: {} not found.", id);
                    return new RoleNotFoundException("Role with ID " + id + " not found.");
                });
    }

    @Override
    public Set<Role> getRolesByIds(Set<Integer> roleIds) {
        log.info("Fetching roles with IDs: {}", roleIds);

        if (roleIds == null || roleIds.isEmpty()) {
            log.warn("No role IDs provided.");
            return Collections.emptySet();
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));

        if (roles.isEmpty()) {
            log.error("No roles found for the provided IDs: {}", roleIds);
            throw new RoleNotFoundException("No roles found for the provided IDs.");
        }

        log.info("Found {} roles for the provided IDs.", roles.size());
        return roles;
    }

    @Override
    public void deleteRoleById(Integer id) {
        log.info("Attempting to mark role with ID: {} as inactive.", id);

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role with ID " + id + " not found."));

        role.setStatus(false);
        roleRepository.save(role);
        log.info("Successfully marked role with ID: {} as inactive.", id);
    }
}
