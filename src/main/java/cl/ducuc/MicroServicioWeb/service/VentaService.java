package cl.ducuc.MicroServicioWeb.service;

import cl.ducuc.MicroServicioWeb.model.VentaModel;
import cl.ducuc.MicroServicioWeb.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public VentaModel crearVenta(VentaModel venta) {
        return ventaRepository.save(venta);
    }
}