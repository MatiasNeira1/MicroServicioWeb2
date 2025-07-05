package cl.ducuc.MicroServicioWeb.controller;

import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody VentaModel ventaModel) {
        try {
            VentaModel ventaRegistrada = ventaService.registrarVenta(ventaModel);
            return ResponseEntity.ok(ventaRegistrada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar la venta");
        }
    }


    @GetMapping
    public ResponseEntity<List<VentaModel>> obtenerTodasLasVentas() {
        List<VentaModel> ventas = ventaService.obtenerTodasLasVentas();
        return ResponseEntity.ok(ventas);
    }


    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<?> obtenerVentasPorCliente(@PathVariable Long idCliente) {
        try {
            List<VentaModel> ventas = ventaService.obtenerVentasPorCliente(idCliente);
            return ResponseEntity.ok(ventas);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
    @GetMapping("/{idVenta}")
    public ResponseEntity<?> obtenerVentaPorId(@PathVariable Long idVenta) {
        Optional<VentaModel> venta = ventaService.obtenerVentaPorId(idVenta);
        if (venta.isPresent()) {
            return ResponseEntity.ok(venta.get());
        }
        return ResponseEntity.notFound().build();
    }
}

