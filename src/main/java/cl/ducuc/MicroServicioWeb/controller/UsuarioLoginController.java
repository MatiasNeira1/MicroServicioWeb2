package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/usuario")
public class UsuarioLoginController {
    @Autowired
    private UsuarioAuth usuarioService;


    @PostMapping("/registrar")

    public ResponseEntity <UserLoginRequest> registrar(@RequestBody UserLoginRequest usuario) {
        try {
            UserLoginRequest usuarioRegistrado = usuarioService.registrarUsuario(usuario.getEmail(), usuario.getContraseña(), usuario.getNombre(), usuario.getApellido());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    @PostMapping("/iniciar")

    public ResponseEntity<String> iniciar(@RequestBody UserLoginRequest usuario) {
        try {
            UserLoginRequest usuarioiniciado = usuarioService.iniciar(usuario.getEmail(), usuario.getContraseña());
            if (usuarioiniciado != null) {
                return ResponseEntity.ok("Usuario iniciado correctamente: " + usuarioiniciado.getEmail());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerTodosUsuarios() {
        try {
            List<UserLoginRequest> usuario = usuarioService.obtenerTodosUsuarios();
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
