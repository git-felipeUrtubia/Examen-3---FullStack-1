package com.empresa.ecomarket2.controller;


import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.service.MonitorService;
import com.empresa.ecomarket2.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsuarioService  usuarioService;

    @MockBean
    MonitorService monitorService;

    @Autowired
    ObjectMapper objectMapper;

    Usuario usuario;

    @BeforeEach
    void setUp() {

        usuario = new Usuario();
        usuario.setId_usuario(1L);
        usuario.setNom_usuario("Felipe");
        usuario.setEmail_usuario("example@duocuc.cl");
        usuario.setPassd_usuario("1234");
        usuario.setRol_usuario("admin");
        usuario.setState_usuario(true);
        usuario.setFecha_creacion_usuario("2000-09-26");
    }

    @Test
    public void testListarUsuarios() throws Exception {

        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].id_usuario").value(1L))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].nom_usuario").value("Felipe"))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].email_usuario").value("example@duocuc.cl"))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].passd_usuario").value("1234"))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].rol_usuario").value("admin"))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].state_usuario").value(true))

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$[0].fecha_creacion_usuario").value("2000-09-26"));

    }




}
