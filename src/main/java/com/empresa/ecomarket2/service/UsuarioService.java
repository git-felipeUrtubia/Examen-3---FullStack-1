package com.empresa.ecomarket2.service;

import com.empresa.ecomarket2.dto.response.UsuarioDTO;
import com.empresa.ecomarket2.model.Usuario;
import com.empresa.ecomarket2.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //BUSCAR USUARIOS
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //GUARDAR USUARIO
    public String guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return "Usuario guardado com sucesso!";
    }

    //BUSCAR USUARIO POR EL ID
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).get();
    }

    //ACTUALIZAR USUARIO
    public String actualizarUsuario(Long id_usuario, Usuario usuarioActualizado) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario user : usuarios) {
            if (user.getId_usuario() == id_usuario) {
                user.setNom_usuario(usuarioActualizado.getNom_usuario());
                user.setEmail_usuario(usuarioActualizado.getEmail_usuario());
                user.setPassd_usuario(usuarioActualizado.getPassd_usuario());
                user.setRol_usuario(usuarioActualizado.getRol_usuario());
                user.setState_usuario(usuarioActualizado.getState_usuario());
                usuarioRepository.save(user);
                return "Usuario actualizado com sucesso!";
            }
        }
        return "";
    }

    //ELIMINAR USUARIO
    public void eliminarUsuario(Long id_usuario) {
        usuarioRepository.deleteById(id_usuario);
    }

    //ELIMINAR TODOS LOS USUARIOS
    public String eliminarTodo() {
        usuarioRepository.deleteAll();
        return "Usuarios eliminado con exito!";
    }

    //SERVICIO DE LOGIN
    public UsuarioDTO LoginService(String email_usuario, String passd_usuario) {

        for (Usuario user : usuarioRepository.findAll()) {

            //Verificamos si el usuario existe
            if (user.getEmail_usuario().equals(email_usuario) &&
                user.getPassd_usuario().equals(passd_usuario)) {

                //Verificamos si el rol del usuario es admin o no
                if (user.getRol_usuario().equals("ADMIN") || user.getRol_usuario().equals("admin")) {

                    UsuarioDTO usuarioAdmin = new UsuarioDTO(
                            user.getId_usuario(),
                            user.getNom_usuario(),
                            user.getEmail_usuario(),
                            user.getPassd_usuario(),
                            user.getRol_usuario(),
                            user.getFecha_creacion_usuario());
                    return usuarioAdmin;
                }else {

                    UsuarioDTO usuarioNoAdmin = new UsuarioDTO(
                            user.getNom_usuario(),
                            user.getEmail_usuario(),
                            user.getRol_usuario());
                    return usuarioNoAdmin;
                }
            }
        }
        //Retorna null si el usuario no existe
        return null;
    }

    //VERIFICAR ROL
    public String verificarRol(Long id_usuario) {
        for (Usuario user : usuarioRepository.findAll()) {
            if (user.getId_usuario() == id_usuario) {
                return user.getRol_usuario();
            }
        }
        return "Usuario no encontrado!";
    }

    //CAMBIAR ROL
    public String cambiarRol(
            Long id_user_cambiar,
            String rol_user_cambiar,
            Long id_user_verificar) {
        String rol = verificarRol(id_user_verificar);
        if (rol.equals("admin")) {
            for (Usuario user : usuarioRepository.findAll()) {
                if (user.getId_usuario() == id_user_cambiar) {
                    user.setRol_usuario(rol_user_cambiar);
                    usuarioRepository.save(user);
                    return user.getRol_usuario();
                }
            }
        }else {
            return "Usuario con acceso denegado!";
        }
        return "";
    }

//    public void crearUsuario(CrearUsuarioRequest request) {
//        Usuario nuevoUsuario = new Usuario();
//        nuevoUsuario.setNom_usuario(request.getNom_usuario());
//        nuevoUsuario.setEmail_usuario(request.getEmail_usuario());
//        nuevoUsuario.setPassd_usuario(request.getPassd_usuario());
//        nuevoUsuario.setRol_usuario("cliente"); // Este campo era obligatorio
//        nuevoUsuario.setFecha_creacion_usuario(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
//        nuevoUsuario.setState_usuario(true);
//        usuarioRepository.save(nuevoUsuario);
//    }

}
