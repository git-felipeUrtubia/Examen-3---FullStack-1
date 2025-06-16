package com.empresa.ecomarket2.service;


import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BackupService {

    //EXTRAER
    public List<Usuario> exportUsuarios(List<Usuario> usuarios) {
        return usuarios;
    }

    //GUARDAR
    public List<Usuario> importUsuarios(List<Usuario> usuarios) {
        List<Usuario> usuariosImportados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosImportados.add(usuario);
        }
        return usuariosImportados;
    }

}
