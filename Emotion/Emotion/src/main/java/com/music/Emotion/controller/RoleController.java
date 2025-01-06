package com.music.Emotion.controller;

import com.music.Emotion.model.dto.RoleRequest;
import com.music.Emotion.model.dto.RoleResponse;
import com.music.Emotion.service.IRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final IRoleService roleService;

    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        log.info("Received request to get all roles"); //Inicializando el método
        List<RoleResponse> roles = roleService.getAllRoles();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        log.info("Returning {} roles", roles.size()); // Finalizando para saber cuantos roles se encontró
        return ResponseEntity.ok(roles);
    }

    // Obtener un rol por su ID
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findRoleById(@PathVariable Integer id) {
        log.info("Received request to find role with ID: {}", id); // Inicio del método con el id buscado
        RoleResponse roleResponse = roleService.findById(id);
        log.info("Returning role with ID: {}", id); // Indicando que se encontró y se está retornando el role
        return ResponseEntity.ok(roleResponse);
    }

    // Guardar un nuevo rol
    @PostMapping
    public ResponseEntity<RoleResponse> saveRole(@Valid @RequestBody RoleRequest roleRequest) {
        log.info("Received request to save new role: {}", roleRequest); // Inicializando el método
        RoleResponse roleResponse = roleService.saveRole(roleRequest);
        log.info("Successfully saved role with ID: {}", roleResponse.getId()); // Finalizando tras la ejecución exitosa
        return ResponseEntity
                .created(URI.create("/api/v1/roles/" + roleResponse.getId()))
                .body(roleResponse);
    }

    // Actualizar un rol existente
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Integer id,
            @Valid @RequestBody RoleRequest roleRequest) {
        log.info("Received request to update role with ID: {}", id);
        RoleResponse roleResponse = roleService.updateRole(id, roleRequest); // Inicializando el método
        log.info("Successfully updated role with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.ok(roleResponse);
    }

    // Eliminar un rol por su ID (lógicamente)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Integer id) {
        log.info("Received request to delete role with ID: {}", id); // Inicializando el método
        roleService.deleteRoleById(id);
        log.info("Successfully deleted role with ID: {}", id); // Finalizando tras la ejecución exitosa
        return ResponseEntity.noContent().build();
    }
}
