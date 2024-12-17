package com.music.Emotion.service;

import com.music.Emotion.model.dto.RoleRequest;
import com.music.Emotion.model.dto.RoleResponse;
import com.music.Emotion.model.entity.Role;

import java.util.List;
import java.util.Set;

public interface IRoleService {

    // Listar todos los roles
    List<RoleResponse> getAllRoles();

    // Guardar un nuevo rol
    public RoleResponse saveRole(RoleRequest roleRequest);

    // Actualizar un rol existente
    public RoleResponse updateRole(Integer id, RoleRequest roleRequest);

    // Buscar un rol por su ID
    public RoleResponse findById(Integer id);

    // Obtener roles por un conjunto de IDs
    Set<Role> getRolesByIds(Set<Integer> roleIds);

    // Eliminar un rol por su ID (lógica de eliminación, no eliminación física)
    void deleteRoleById(Integer id);
}
