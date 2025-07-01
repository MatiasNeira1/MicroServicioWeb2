package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.model.DTOProduct;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import cl.ducuc.MicroServicioWeb.service.VentaService;
import cl.ducuc.MicroServicioWeb.service.ProductService;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UsuarioAuth usuarioService;

    @PostMapping("/crear")
    public ResponseEntity<VentaModel> crearVenta(@RequestBody VentaModel venta) {
        VentaModel nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity.ok(nuevaVenta);
    }
}