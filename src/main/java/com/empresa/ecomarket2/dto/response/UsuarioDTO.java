package com.empresa.ecomarket2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class UsuarioDTO {

    private Long id_usuario;
    private String nom_usuario;
    private String email_usuario;
    private String passd_usuario;
    private String rol_usuario;
    private String fecha_usuario_creacion;

    public UsuarioDTO(String nom_usuario, String email_usuario, String rol_usuario) {
        this.nom_usuario = nom_usuario;
        this.email_usuario = email_usuario;
        this.rol_usuario = rol_usuario;
    }

    public UsuarioDTO(
            Long id_usuario,
            String nom_usuario,
            String email_usuario,
            String passd_usuario,
            String rol_usuario,
            String fecha_usuario_creacion) {
        this.id_usuario = id_usuario;
        this.nom_usuario = nom_usuario;
        this.email_usuario = email_usuario;
        this.passd_usuario = passd_usuario;
        this.rol_usuario = rol_usuario;
        this.fecha_usuario_creacion = fecha_usuario_creacion;
    }

}
