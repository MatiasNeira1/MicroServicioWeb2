package cl.ducuc.MicroServicioWeb.controller;


import cl.ducuc.MicroServicioWeb.model.UserLoginRequest;
import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.service.ProductService;
import cl.ducuc.MicroServicioWeb.service.UsuarioAuth;
import cl.ducuc.MicroServicioWeb.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping("/ventas")
@RestController
public class VentaController {

    @Autowired
    private VentaService ventaService;
    private UsuarioAuth usuarioservice;
    private ProductService productservice;


    //public ResponseEntity<VentaModel> crearVenta(@RequestBody VentaModel venta, @PathVariable Long idUsuario, @PathVariable Long id_prod) {
      //  try {
        //    Optional<UserLoginRequest> usuario_encontado = usuarioservice.obtenerUsuarioPorId(idUsuario);


          //   return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
       // } catch (Exception e) {
         //   return ResponseEntity.status(500).body(null);
       // }
    }
//}
