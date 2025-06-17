package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.config.WebClientConfig;
import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProductService {
    @Autowired
    private WebClient webClient;

    public DTOProduct[] getAllProducts() {
        return webClient
                .get()
                .uri("/inventario/inventarios")
                .retrieve()
                .bodyToMono(DTOProduct[].class)
                .block();
    }

    }

