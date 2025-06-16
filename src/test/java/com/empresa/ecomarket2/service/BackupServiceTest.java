package com.empresa.ecomarket2.service;


import com.empresa.ecomarket2.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BackupServiceTest {

    @Autowired
    BackupService backupService;

    @Test
    public void testExportUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario user = new Usuario(
                1L,
                "Felipe",
                "fel.urt@duoc.cl",
                "1234",
                "admin",
                true,
                "2000-09-26"
        );
        usuarios.add(user);

        List<Usuario> export = backupService.exportUsuarios(usuarios);

        assertEquals(1, export.size());
        assertEquals("Felipe", export.get(0).getNom_usuario());
        assertEquals("admin",  export.get(0).getRol_usuario());
    }

    @Test
    public void testImportUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario user = new Usuario(
                1L,
                "Felipe",
                "fel.urt@duoc.cl",
                "1234",
                "admin",
                true,
                "2000-09-26"
        );
        usuarios.add(user);
        backupService.exportUsuarios(usuarios);
        List<Usuario> importUsuario = backupService.importUsuarios(usuarios);

        assertNotNull(importUsuario);
        assertEquals(1, importUsuario.size());
    }


}
