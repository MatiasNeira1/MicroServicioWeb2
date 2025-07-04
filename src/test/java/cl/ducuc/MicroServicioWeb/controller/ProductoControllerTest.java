package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import cl.ducuc.MicroServicioWeb.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductoController productoController;

    @Test
    void testGetProductos() {
        DTOProduct[] productosMock = new DTOProduct[]{
                new DTOProduct(1L, "Producto 1", "Descripción 1", 10, 1000),
                new DTOProduct(2L, "Producto 2", "Descripción 2", 5, 2000)
        };
        when(productService.getAllProducts()).thenReturn(productosMock);

        DTOProduct[] resultado = productoController.getProductos();

        assertArrayEquals(productosMock, resultado);
        verify(productService, times(1)).getAllProducts();
    }
}