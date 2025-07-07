package cl.ducuc.MicroServicioWeb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "venta")
public class VentaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idventa;
    private Long idusuario;
    private Long id_prod;
    private String nombreProducto;
    private int precioProducto;
    private int cantidadProducto;



    @ManyToOne
    @JoinColumn(name = "idusuario", insertable = false, updatable = false)
    private UserLoginRequest usuario;
}