package com.empresa.ecomarket2.controller;

import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.service.BackupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/backup")
@Tag(name = "Backup", description = "Operaciones relacionadas con el respaldo de datos")
public class BackupController {

    @Autowired
    BackupService backupService;

    List<Usuario> bbdd = new ArrayList<>();

    @PostMapping("/respaldo")
    @Operation(
            summary = "Guardar datos",
            description = "Crea un respaldo de todos los datos de la Base de Datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa: respaldo creado"),
            @ApiResponse(responseCode = "204", description = "No se encontraron datos para respaldar"),
            @ApiResponse(responseCode = "500", description = "Error interno al crear el respaldo")
    })
    public ResponseEntity<String> respaldarUsuarios(@RequestBody List<Usuario> usuarios) {
        List<Usuario> listaUsers = backupService.importUsuarios(usuarios);
        bbdd.addAll(listaUsers);
        if (usuarios.isEmpty() || listaUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("Respaldo creado con exito!");
    }

    @GetMapping("/recuperar")
    @Operation(
            summary = "Recuperar datos",
            description = "Recupera los datos respaldados previamente desde la Base de Datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa: datos recuperados"),
            @ApiResponse(responseCode = "204", description = "No hay datos para recuperar"),
            @ApiResponse(responseCode = "500", description = "Error interno al recuperar los datos")
    })
    public ResponseEntity<List<Usuario>> recuperarUsuarios() {
        List<Usuario> usuarios = backupService.exportUsuarios(bbdd);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

}
