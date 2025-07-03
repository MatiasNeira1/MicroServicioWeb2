package cl.ducuc.MicroServicioWeb.repository;

import cl.ducuc.MicroServicioWeb.model.VentaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<VentaModel, Long> {
    List<VentaModel> findByIdusuario(Long idUsuario);
}