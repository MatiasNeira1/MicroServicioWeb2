package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioAuthTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioAuth usuarioAuth;

    private UserLoginRequest usuario;

    @BeforeEach
    void setUp() {
        usuario = new UserLoginRequest();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@gmail.com");
        usuario.setContraseña("encodedPassword");
    }

    @Test
    void registrarUsuario() {

        String rawPassword = "password123";
        when(passwordEncoder.encode(rawPassword)).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(UserLoginRequest.class))).thenReturn(usuario);


        UserLoginRequest result = usuarioAuth.registrarUsuario(
                "juan@gmail.com",
                rawPassword,
                "Juan",
                "Perez");


        assertNotNull(result);
        assertEquals("encodedPassword", result.getContraseña());
        assertEquals("juan@gmail.com", result.getEmail());
        assertEquals("Juan", result.getNombre());
        assertEquals("Perez", result.getApellido());

        verify(passwordEncoder).encode(rawPassword);
        verify(usuarioRepository).save(any(UserLoginRequest.class));
    }

    @Test
    void iniciar_ConValidacionesDeCredenciales() {
        // Arrange
        String email = "juan@gmail.com";
        String rawPassword = "password123";

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
        when(passwordEncoder.matches(rawPassword, usuario.getContraseña())).thenReturn(true);


        UserLoginRequest result = usuarioAuth.iniciar(email, rawPassword);


        assertNotNull(result);
        assertEquals(usuario, result);
        verify(usuarioRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, usuario.getContraseña());
    }

    @Test
    void iniciar_ConInvalidPassword() {

        String email = "juan@gmail.com";
        String wrongPassword = "hola123";

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);
        when(passwordEncoder.matches(wrongPassword, usuario.getContraseña())).thenReturn(false);


        UserLoginRequest result = usuarioAuth.iniciar(email, wrongPassword);


        assertNull(result);
    }

    @Test
    void iniciar_sesion() {

        String email = "hola123@gmail.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(null);


        UserLoginRequest result = usuarioAuth.iniciar(email, "123asdmasd12k3");


        assertNull(result);
    }

    @Test
    void obtenerTodosUsuarios() {

        List<UserLoginRequest> usuarios = Arrays.asList(
                new UserLoginRequest(1L, "Juan", "Perez", "juan@gmail.com", "pass1"),
                new UserLoginRequest(2L, "Maria", "Gomez", "maria@gmail.com", "pass2")
        );
        when(usuarioRepository.findAll()).thenReturn(usuarios);


        List<UserLoginRequest> result = usuarioAuth.obtenerTodosUsuarios();

        assertEquals(2, result.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void obtenerUsuarioPorId() {

        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));


        Optional<UserLoginRequest> result = usuarioAuth.obtenerUsuarioPorId(id);


        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
        verify(usuarioRepository).findById(id);
    }


}