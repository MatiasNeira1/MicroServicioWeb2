package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.*;
import cl.ducuc.MicroServicioWeb.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class  VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UsuarioAuth usuarioService;

    @Transactional
    public VentaModel registrarVenta(VentaModel ventaModel) {

        Optional<UserLoginRequest> usuarioOpt = usuarioService.obtenerUsuarioPorId(ventaModel.getIdusuario());
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }


        DTOProduct producto = productService.getProductById(ventaModel.getId_prod());
        if (producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        if (producto.getStock() < ventaModel.getCantidadProducto()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock());
        }


        ventaModel.setNombreProducto(producto.getNombre());
        ventaModel.setPrecioProducto(producto.getPrecio());


        return ventaRepository.save(ventaModel);
    }

    public List<VentaModel> obtenerTodasLasVentas() {
        return ventaRepository.findAll();
    }


    public List<VentaModel> obtenerVentasPorCliente(Long idCliente) {

        Optional<UserLoginRequest> clienteOpt = usuarioService.obtenerUsuarioPorId(idCliente);
        if (clienteOpt.isEmpty()) {
            throw new RuntimeException("Cliente no encontrado");
        }
        return ventaRepository.findByIdusuario(idCliente);
    }


    public Optional<VentaModel> obtenerVentaPorId(Long idVenta) {
        return ventaRepository.findById(idVenta);
    }
}
