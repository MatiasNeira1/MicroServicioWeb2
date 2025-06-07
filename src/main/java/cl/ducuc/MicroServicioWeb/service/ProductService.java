package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.config.RestTemplateConfig;
import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private RestTemplateConfig restTemplate;

    private final String PRODUCT_MICROSERVICE_URL="http://localhost:8093/inventario/inventarios";

    public DTOProduct[] getAllProducts() {
        return restTemplate.restTemplate().getForObject(PRODUCT_MICROSERVICE_URL, DTOProduct[].class);
    }
    }

