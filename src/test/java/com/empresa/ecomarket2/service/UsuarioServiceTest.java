package com.empresa.ecomarket2.service;


import com.empresa.ecomarket2.dto.response.UsuarioDTO;
import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;


    @Test
    public void testListarUsuarios() {

        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(
                1L,
                "felipe",
                "fel.urt@duoc.cl",
                "123-k",
                "admin",
                true,
                "2000-09-26")));

        List<Usuario> usuarios = usuarioService.listarUsuarios();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());

    }

    @Test
    public void testGuardarUsuarios() throws ParseException {

        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sft.parse("2000-09-26");

        Usuario usuario = new Usuario(
                1L,
                "felipe",
                "fel.urt@duoc.cl",
                "123-k",
                "admin",
                true,
                "2000-09-26");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        String saved = usuarioService.guardarUsuario(usuario);
        assertNotNull(saved);
        assertEquals("Usuario guardado com sucesso!", saved);

    }

    @Test
    public void testBuscarPorId() throws ParseException {

        SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sft.parse("2000-09-26");

        Usuario usuario = new Usuario(
                1L,
                "felipe",
                "fel.urt@duoc.cl",
                "123-k",
                "admin",
                true,
                "2000-09-26");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario usuarioBuscado = usuarioService.buscarPorId(1L);
        assertNotNull(usuarioBuscado);
        assertEquals(usuario, usuarioBuscado);

    }

    @Test
    public void  testActualizarUsuario() {

        Usuario usuario = new Usuario(
                1L,
                "felipe",
                "fel.urt@duoc.cl",
                "123-k",
                "admin",
                true,
                "2000-09-26");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuario.setNom_usuario("mclovin");
        String usuarioActualizado = usuarioService.actualizarUsuario(
                1L, usuario);
        assertNotNull(usuarioActualizado);
        assertEquals("mclovin", usuario.getNom_usuario());

    }

    @Test
    public void testEliminarUsuario() {

        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.eliminarUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);

    }

    @Test
    public void testEliminarTodo() {

        doNothing().when(usuarioRepository).deleteAll();

        usuarioService.eliminarTodo();
        verify(usuarioRepository, times(1)).deleteAll();

    }

    @Test
    public void testLoginService() {

        Usuario usuario = new Usuario(
                1L,
                "Felipe",
                "fel.urt@duoc.cl",
                "1234",
                "ADMIN",
                true,
                "2000-09-26"
        );

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        UsuarioDTO res = usuarioService.LoginService(
                "fel.urt@duoc.cl",
                "1234");

        assertNotNull(res);
    }

    @Test
    public void testVerificarRol() {

        Usuario usuario = new Usuario(
                1L,
                "Felipe",
                "fel.urt@duoc.cl",
                "1234",
                "ADMIN",
                true,
                "2000-09-26"
        );

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        String rol = usuarioService.verificarRol(1L);

        assertNotNull(rol);
        assertEquals("ADMIN", rol);
    }

    @Test
    public void testCambiarRol() {

        Usuario usuario = new Usuario(
                1L,
                "Felipe",
                "fel.urt@duoc.cl",
                "1234",
                "admin",
                true,
                "2000-09-26"
        );

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        String rol = usuarioService.cambiarRol(
                1L,
                "user",
                1L);

        assertNotNull(rol);
        assertEquals("user", rol);

    }

}
