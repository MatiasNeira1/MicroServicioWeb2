package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RequestHeadersSpec requestHeadersSpec;

    @Mock
    private ResponseSpec responseSpec;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts() {

        DTOProduct[] expectedProducts = new DTOProduct[]{
                new DTOProduct(1L, "Producto 1", "Descripción 1", 10, 1000),
                new DTOProduct(2L, "Producto 2", "Descripción 2", 20, 2000)
        };

        mockWebClientChain();
        when(responseSpec.bodyToMono(DTOProduct[].class)).thenReturn(Mono.just(expectedProducts));


        DTOProduct[] actualProducts = productService.getAllProducts();


        assertArrayEquals(expectedProducts, actualProducts);
        verify(webClient).get();
        verify(requestHeadersUriSpec).uri("/producto/todos");
        verify(responseSpec).bodyToMono(DTOProduct[].class);
    }

    @Test
    void getProductById() {

        Long productId = 1L;
        DTOProduct expectedProduct = new DTOProduct(productId, "Producto Test", "Descripción Test", 15, 1500);

        mockWebClientChain();
        when(requestHeadersUriSpec.uri("/producto/{id}", productId)).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(DTOProduct.class)).thenReturn(Mono.just(expectedProduct));


        DTOProduct actualProduct = productService.getProductById(productId);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct.getId(), actualProduct.getId());
        assertEquals(expectedProduct.getNombre(), actualProduct.getNombre());
        assertEquals(expectedProduct.getDescripcion(), actualProduct.getDescripcion());
        assertEquals(expectedProduct.getStock(), actualProduct.getStock());
        assertEquals(expectedProduct.getPrecio(), actualProduct.getPrecio());
    }

    @Test
    void getProductById_ConIdInvalida() {

        Long invalidId = 999L;

        mockWebClientChain();
        when(requestHeadersUriSpec.uri("/producto/{id}", invalidId)).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(DTOProduct.class)).thenReturn(Mono.empty());


        DTOProduct result = productService.getProductById(invalidId);


        assertNull(result);
    }

    private void mockWebClientChain() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }
}