package cl.ducuc.MicroServicioWeb.repository;

import cl.ducuc.MicroServicioWeb.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<VentaModel, Long> {
}
