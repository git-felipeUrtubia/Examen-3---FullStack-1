package com.empresa.ecomarket2.controller;

import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/backup")
public class BackupController {

    @Autowired
    BackupService backupService;

    List<Usuario> bbdd = new ArrayList<>();

    @PostMapping("/respaldo")
    public ResponseEntity<String> respaldarUsuarios(@RequestBody List<Usuario> usuarios) {
        List<Usuario> listaUsers = backupService.importUsuarios(usuarios);
        bbdd.addAll(listaUsers);
        if (usuarios.isEmpty() || listaUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok("Respaldo creado con exito!");
    }

    @GetMapping("/recuperar")
    public ResponseEntity<List<Usuario>> recuperarUsuarios() {
        List<Usuario> usuarios = backupService.exportUsuarios(bbdd);
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

}
