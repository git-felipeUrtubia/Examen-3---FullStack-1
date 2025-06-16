package com.empresa.ecomarket2.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(nullable = false)
    private String nom_usuario;

    @Column(nullable = false, unique = true)
    private String email_usuario;

    @Column(nullable = false)
    private String passd_usuario;

    @Column(nullable = false)
    private String rol_usuario;

    @Column(nullable = false)
    private Boolean state_usuario;

    @Column(nullable = false)
    private String fecha_creacion_usuario;
}
