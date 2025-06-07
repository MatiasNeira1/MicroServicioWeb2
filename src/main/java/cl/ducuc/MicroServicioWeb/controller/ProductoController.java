package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import cl.ducuc.MicroServicioWeb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productos")
    public DTOProduct[] getAllProducts() {
        return productService.getAllProducts();
    }


}

