package com.empresa.ecomarket2.controller;

import com.empresa.ecomarket2.dto.response.MonitorDTO;
import com.empresa.ecomarket2.dto.response.UsuarioDTO;
import com.empresa.ecomarket2.model.Monitor;
import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.service.MonitorService;
import com.empresa.ecomarket2.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Usuarios", description = "Operaciones relacionadas con el usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    MonitorService monitorService;

    //LISTAR USUARIOS
    @GetMapping
    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(usuarios);
    }

    //BUSCAR USUARIO POR ID
    @GetMapping("/id:{id_usuario}")
    @Operation(summary = "Obtener usuario por id", description = "Obtiene un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
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
    @Operation(summary = "Obtiene un mensaje", description = "Obtiene un String indicando que el usuario a sido guardado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Usuario no guardado")
    })
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
    @Operation(summary = "Actualiza un usuario", description = "Actualiza la información de un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "403", description = "Usuario con acceso denegado"),
            @ApiResponse(responseCode = "500", description = "Error interno al actualizar el usuario")
    })
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
    @Operation(summary = "Elimina un usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar el usuario")
    })
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
    @Operation(summary = "Elimina todos los usuarios", description = "Elimina todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos los usuarios eliminados exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar los usuarios")
    })
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
    @Operation(summary = "Inicio de sesión de usuario", description = "Verifica las credenciales del usuario y retorna sus datos si son correctas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas o usuario no encontrado")
    })
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
    @Operation(summary = "Actualizar rol de un usuario", description = "Actualiza el rol de un usuario dado el ID del usuario que hace el cambio y el ID del usuario objetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado para actualizar el rol")
    })
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
