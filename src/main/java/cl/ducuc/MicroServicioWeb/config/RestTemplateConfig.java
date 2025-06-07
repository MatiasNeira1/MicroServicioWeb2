package cl.ducuc.MicroServicioWeb.config;

import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public DTOProduct[] getAllProducts() {
        String PRODUCT_MICROSERVICE_URL = "http://localhost:8093/inventario/inventarios";
        return restTemplate().getForObject(PRODUCT_MICROSERVICE_URL, DTOProduct[].class);
    }

}
