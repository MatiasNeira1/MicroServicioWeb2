package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UsuarioAuth usuarioService;

    @InjectMocks
    private VentaService ventaService;

    private VentaModel ventaModel;
    private DTOProduct producto;
    private UserLoginRequest usuario;

    @BeforeEach
    void setUp() {

        usuario = new UserLoginRequest();
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@gmail.com");

        producto = new DTOProduct();
        producto.setId(100L);
        producto.setNombre("Producto Test");
        producto.setPrecio(10000);
        producto.setStock(50);

        ventaModel = new VentaModel();
        ventaModel.setIdusuario(1L);
        ventaModel.setId_prod(100L);
        ventaModel.setCantidadProducto(2);
    }

    @Test
    void registrarVenta_ConDatosValidos() {
        // Arrange
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(productService.getProductById(100L)).thenReturn(producto);
        when(ventaRepository.save(any(VentaModel.class))).thenAnswer(invocation -> {
            VentaModel venta = invocation.getArgument(0);
            venta.setIdventa(1L);
            return venta;
        });


        VentaModel resultado = ventaService.registrarVenta(ventaModel);


        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdventa());
        assertEquals("Producto Test", resultado.getNombreProducto());
        assertEquals(10000, resultado.getPrecioProducto());
        assertEquals(2, resultado.getCantidadProducto());

        verify(usuarioService).obtenerUsuarioPorId(1L);
        verify(productService).getProductById(100L);
        verify(ventaRepository).save(any(VentaModel.class));
    }

    @Test
    void registrarVenta_ConUsuarioInexistente() {

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.registrarVenta(ventaModel);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioService).obtenerUsuarioPorId(1L);
        verifyNoInteractions(productService, ventaRepository);
    }

    @Test
    void registrarVenta_ConProductoInexistente_DeberiaLanzarExcepcion() {

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(productService.getProductById(100L)).thenReturn(null);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.registrarVenta(ventaModel);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(usuarioService).obtenerUsuarioPorId(1L);
        verify(productService).getProductById(100L);
        verifyNoInteractions(ventaRepository);
    }

    @Test
    void registrarVenta() {

        ventaModel.setCantidadProducto(100);
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(productService.getProductById(100L)).thenReturn(producto);


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.registrarVenta(ventaModel);
        });

        assertEquals("Stock insuficiente. Disponible: 50", exception.getMessage());
        verify(usuarioService).obtenerUsuarioPorId(1L);
        verify(productService).getProductById(100L);
        verifyNoInteractions(ventaRepository);
    }

    @Test
    void obtenerTodasLasVentas() {
        // Arrange
        VentaModel venta1 = new VentaModel();
        venta1.setIdventa(1L);
        VentaModel venta2 = new VentaModel();
        venta2.setIdventa(2L);

        when(ventaRepository.findAll()).thenReturn(Arrays.asList(venta1, venta2));

        // Act
        List<VentaModel> resultado = ventaService.obtenerTodasLasVentas();

        // Assert
        assertEquals(2, resultado.size());
        verify(ventaRepository).findAll();
    }

    @Test
    void obtenerVentasPorCliente_ConClienteExistente() {
        // Arrange
        VentaModel venta1 = new VentaModel();
        venta1.setIdventa(1L);
        venta1.setIdusuario(1L);

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(ventaRepository.findByIdusuario(1L)).thenReturn(Arrays.asList(venta1));

        // Act
        List<VentaModel> resultado = ventaService.obtenerVentasPorCliente(1L);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdventa());
        verify(usuarioService).obtenerUsuarioPorId(1L);
        verify(ventaRepository).findByIdusuario(1L);
    }

    @Test
    void obtenerVentasPorCliente() {

        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventaService.obtenerVentasPorCliente(1L);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(usuarioService).obtenerUsuarioPorId(1L);
        verifyNoInteractions(ventaRepository);
    }

    @Test
    void obtenerVentaPorId() {

        VentaModel venta = new VentaModel();
        venta.setIdventa(1L);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));


        Optional<VentaModel> resultado = ventaService.obtenerVentaPorId(1L);


        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getIdventa());
        verify(ventaRepository).findById(1L);
    }


}