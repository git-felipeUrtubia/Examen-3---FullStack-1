package com.empresa.ecomarket2.controller;

import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.dto.response.UsuarioDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.service.MonitorService;
import com.empresa.ecomarket2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MonitorService monitorService;

    //LISTAR USUARIOS
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(usuarios);
    }
    //BUSCAR USUARIO POR ID
    @GetMapping("/id:{id_usuario}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id_usuario) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id_usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            String error = "Error al obtener usuario con ID " + id_usuario + ": " + e.getMessage();

            Monitor monitor = new Monitor();
            monitor.setStatus("ERROR");
            monitor.setMessage(error);
            monitor.setTimestamp(LocalDateTime.now());
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            monitorService.agregarError(monitorDTO);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + error);
        }
    }
    // GUARDAR USUARIO
    @PostMapping
    public ResponseEntity<String> guardarUsuario(@RequestBody Usuario usuario) {
        String mensaje = "";
        try {
            mensaje = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Error al guardar usuario: " + e.getMessage();

            Monitor monitor = new Monitor();
            monitor.setStatus("ERROR");
            monitor.setMessage(error);
            monitor.setTimestamp(LocalDateTime.now());
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            monitorService.agregarError(monitorDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //ACTUALIZAR USUARIO
    @PutMapping("/{id_usuario}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id_usuario, @RequestBody Usuario usuario) {

        for (Usuario user : usuarioService.listarUsuarios()) {
            if (user.getId_usuario() == id_usuario) {
                String rol = usuarioService.verificarRol(id_usuario);
                if (rol.equals("USER") || rol.equals("user")) {
                    return ResponseEntity.ok("Usuario con acceso denegado");
                }
            }
        }

        String mensaje = usuarioService.actualizarUsuario(id_usuario, usuario);
        try {
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Error al actualizar usuario: " + e.getMessage();

            Monitor monitor = new Monitor();
            monitor.setStatus("ERROR");
            monitor.setMessage(error);
            monitor.setTimestamp(LocalDateTime.now());
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            monitorService.agregarError(monitorDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //ELIMINAR USUARIO POR ID
    @DeleteMapping("/{id_usuario}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id_usuario) {
        try {
            usuarioService.eliminarUsuario(id_usuario);
            return ResponseEntity.ok("Usuario con eliminado");
        } catch (Exception e) {
            String error = "Error al eliminar usuario: " + e.getMessage();

            Monitor monitor = new Monitor();
            monitor.setStatus("ERROR");
            monitor.setMessage(error);
            monitor.setTimestamp(LocalDateTime.now());
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            monitorService.agregarError(monitorDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //ELIMINAR TODOS LOS USUARIOS
    @DeleteMapping
    public ResponseEntity<String> eliminarTodosUsuarios() {
        String mensaje = usuarioService.eliminarTodo();
        try {
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            String error = "Error al eliminar todos los usuarios: " + e.getMessage();

            Monitor monitor = new Monitor();
            monitor.setStatus("ERROR");
            monitor.setMessage(error);
            monitor.setTimestamp(LocalDateTime.now());
            MonitorDTO monitorDTO = new MonitorDTO(monitor);
            monitorService.agregarError(monitorDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //LOGIN DE USUARIOS
    @GetMapping("/login/{email_usuario}/{passd_usuario}")
    public ResponseEntity<?> Login(@PathVariable String email_usuario, @PathVariable String passd_usuario) {

        UsuarioDTO usuarioDTO = usuarioService.LoginService(email_usuario, passd_usuario);
        if (usuarioDTO == null) {
            //Map -> estructura de datos tipo diccionario ( clave, valor )
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "El usuario no existe o las credenciales son incorrectas");

            //401 UNAUTHORIZED → significa “credenciales inválidas”
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(usuarioDTO);
    }
    //ACTUALIZAR ROL
    @GetMapping("/updateRol/user/{id_user}/id/{id_user_cambiar}/rol/{rol_user_cambiar}")
    public ResponseEntity<String> ActualizarRol(
            @PathVariable Long id_user,
            @PathVariable Long id_user_cambiar,
            @PathVariable String rol_user_cambiar ) {
        String mensaje = usuarioService.cambiarRol(
                id_user_cambiar,
                rol_user_cambiar,
                id_user);
        if (mensaje == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(rol_user_cambiar);
        }
        return ResponseEntity.ok(mensaje);
    }

}
