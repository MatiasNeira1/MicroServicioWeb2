package cl.ducuc.MicroServicioWeb.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DTOProduct {
    private long id;
    private String nombre;
    private String descripcion;
    private int stock;
    private int precio;

}
