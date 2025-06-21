package com.empresa.ecomarket2.controller;


import com.empresa.ecomarket2.dto.response.UsuarioDTO;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void testActualizarUsuario() throws Exception {
        when(usuarioService.actualizarUsuario(any(Long.class), any(Usuario.class)))
                .thenReturn("Usuario actualizado com sucesso!");

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Usuario actualizado com sucesso!"));
    }

    @Test
    public void testEliminarUsuario() throws Exception {

        doNothing().when(usuarioService).eliminarUsuario(1L);

        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).eliminarUsuario(1L);

    }

    @Test
    public void testEliminarTodosUsuarios() throws Exception {

        when(usuarioService.eliminarTodo()).thenReturn("Usuarios eliminado con exito!");

        mockMvc.perform(delete("/api/v1/users"))
                .andExpect(status().isOk());

        verify(usuarioService, times(1)).eliminarTodo();

    }

    @Test
    public void testLogin() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Felipe", "correo@correo.com", "pass123", "ADMIN", "2025-09-12");

        when(usuarioService.LoginService("correo@correo.com", "pass123"))
                .thenReturn(usuarioDTO);

        mockMvc.perform(get("/api/v1/users/login/correo@correo.com/pass123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_usuario").value(1L))
                .andExpect(jsonPath("$.nom_usuario").value("Felipe"))
                .andExpect(jsonPath("$.rol_usuario").value("ADMIN"));
    }

    @Test
    public void testActualizarRol() throws Exception {
        Long id_user = 1L;
        Long id_user_cambiar = 2L;
        String nuevoRol = "admin";

        when(usuarioService.cambiarRol(id_user_cambiar, nuevoRol, id_user))
                .thenReturn("admin");

        mockMvc.perform(get("/api/v1/users/updateRol/user/1/id/2/rol/admin"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("admin"));
    }

}
