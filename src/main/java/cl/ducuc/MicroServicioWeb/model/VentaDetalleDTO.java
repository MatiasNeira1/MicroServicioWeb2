package cl.ducuc.MicroServicioWeb.model;

import lombok.Data;

@Data
public class VentaDetalleDTO {
    private VentaModel venta;
    private UserLoginRequest usuario;
    private DTOProduct producto;
    private int total;
}