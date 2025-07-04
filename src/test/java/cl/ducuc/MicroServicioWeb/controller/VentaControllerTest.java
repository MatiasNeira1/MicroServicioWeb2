package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.service.VentaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VentaControllerTest {

    @Mock
    private VentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    @Test
    void testRegistrarVentaExitoso() {

        UserLoginRequest usuarioMock = new UserLoginRequest(1L, "user@test.com", "password", "Nombre", "Apellido");
        VentaModel ventaMock = new VentaModel();
        ventaMock.setIdventa(1L);
        ventaMock.setIdusuario(1L);
        ventaMock.setId_prod(101L);
        ventaMock.setNombreProducto("Producto Test");
        ventaMock.setPrecioProducto(10000);
        ventaMock.setCantidadProducto(2);
        ventaMock.setUsuario(usuarioMock);

        when(ventaService.registrarVenta(any(VentaModel.class))).thenReturn(ventaMock);


        ResponseEntity<?> respuesta = ventaController.registrarVenta(ventaMock);


        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(ventaMock, respuesta.getBody());
        verify(ventaService, times(1)).registrarVenta(any(VentaModel.class));
    }

    @Test
    void testObtenerTodasLasVentas() {

        UserLoginRequest usuarioMock = new UserLoginRequest(1L, "user@test.com", "password", "Nombre", "Apellido");

        List<VentaModel> ventasMock = Arrays.asList(
                new VentaModel(1L, 1L, 101L, "Producto 1", 10000, 2, usuarioMock),
                new VentaModel(2L, 1L, 102L, "Producto 2", 20000, 1, usuarioMock)
        );

        when(ventaService.obtenerTodasLasVentas()).thenReturn(ventasMock);


        ResponseEntity<List<VentaModel>> respuesta = ventaController.obtenerTodasLasVentas();


        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
        assertEquals("Producto 1", respuesta.getBody().get(0).getNombreProducto());
    }

    @Test
    void testObtenerVentasPorCliente() {

        UserLoginRequest usuarioMock = new UserLoginRequest(1L, "user@test.com", "password", "Nombre", "Apellido");

        List<VentaModel> ventasMock = Arrays.asList(
                new VentaModel(1L, 1L, 101L, "Producto 1", 10000, 2, usuarioMock),
                new VentaModel(2L, 1L, 102L, "Producto 2", 20000, 1, usuarioMock)
        );

        when(ventaService.obtenerVentasPorCliente(1L)).thenReturn(ventasMock);


        ResponseEntity<?> respuesta = ventaController.obtenerVentasPorCliente(1L);


        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, ((List<?>) respuesta.getBody()).size());
    }

    @Test
    void testObtenerVentaPorId() {

        UserLoginRequest usuarioMock = new UserLoginRequest(1L, "user@test.com", "password", "Nombre", "Apellido");
        VentaModel ventaMock = new VentaModel(1L, 1L, 101L, "Producto Test", 10000, 2, usuarioMock);

        when(ventaService.obtenerVentaPorId(1L)).thenReturn(Optional.of(ventaMock));


        ResponseEntity<?> respuesta = ventaController.obtenerVentaPorId(1L);


        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(10000, ((VentaModel) respuesta.getBody()).getPrecioProducto());
    }

    @Test
    void testObtenerVentaPorId_NoEncontrada() {
        when(ventaService.obtenerVentaPorId(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = ventaController.obtenerVentaPorId(999L);

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}