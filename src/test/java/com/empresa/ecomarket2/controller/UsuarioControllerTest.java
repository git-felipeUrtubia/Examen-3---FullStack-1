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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                        .jsonPath("$[0].email_usuario").value("example@duocuc.cl"));
    }

    @Test
    public void testBuscarPorId() throws Exception {

        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/users/id:1"))
                .andExpect(status().isOk())

                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id_usuario").value(1L))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.nom_usuario").value("Felipe"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email_usuario").value("example@duocuc.cl"));
    }

    @Test
    public void testGuardarUsuario() throws Exception {

        when(usuarioService.guardarUsuario(any(Usuario.class)))
                .thenReturn("Usuario guardado con sucesso!");

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Usuario guardado con sucesso!"));
    }



}
