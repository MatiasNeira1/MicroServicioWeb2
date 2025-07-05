package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioLoginControllerTest {

    @Mock
    private UsuarioAuth usuarioService;

    @InjectMocks
    private UsuarioLoginController usuarioController;

    @Test
    void testRegistrarUsuario() {
        UserLoginRequest usuarioMock = new UserLoginRequest(1l,"test@example.com", "123456", "Test", "User");
        when(usuarioService.registrarUsuario(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(usuarioMock);

        ResponseEntity<UserLoginRequest> respuesta = usuarioController.registrar(usuarioMock);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(usuarioMock, respuesta.getBody());
    }

    @Test
    void testIniciarSesionExitoso() {
        new UserLoginRequest(1l, "Pedro", "Lopez", "user1@testsda.com", "pass123324");
    }
    @Test
    void testObtenerTodosUsuarios() {
        List<UserLoginRequest> usuariosMock = Arrays.asList(
                new UserLoginRequest(1l, "pedro", "torres", "user1@test.com", "1234512as"),
                new UserLoginRequest(2l, "juan", "torres2", "user1@test.com", "131dwasasd")
        );

        when(usuarioService.obtenerTodosUsuarios()).thenReturn(usuariosMock);

        ResponseEntity<?> respuesta = usuarioController.obtenerTodosUsuarios();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(usuariosMock, respuesta.getBody());
    }
}