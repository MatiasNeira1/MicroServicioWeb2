package cl.ducuc.MicroServicioWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venta")
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idventa;
    private String nombreProducto;
    private int precioProducto;
    private int cantidadProducto;
    private Long idusuario;

    @OneToMany
    @JoinColumn(name = "idventa")
    private List<UserLoginRequest> usuario;





}
