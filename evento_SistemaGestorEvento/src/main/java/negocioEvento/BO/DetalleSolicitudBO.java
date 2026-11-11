/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTODetalleSolicitud;
import negocioEvento.interfaces.IDetalleSolicitudBO;


/**
 *
 * @author Andres
 */
public class DetalleSolicitudBO implements IDetalleSolicitudBO {

    private final AlmacenDatos almacen;

    public DetalleSolicitudBO(AlmacenDatos almacen) {
        this.almacen = almacen;
    }

    @Override
    public DTODetalleSolicitud crearDetalleSolicitud(DTODetalleSolicitud detalle) {
        int nuevoId = almacen.getDetallesSolicitud().size() + 1;
        detalle.setDetalleSolicitud(nuevoId);
        almacen.getDetallesSolicitud().add(detalle);
        return detalle;
    }
}
