/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocioEvento.BO;
import com.mycompany.persistencia.AlmacenDatos;
import dto.DTODetalleSolicitud;
import dto.DTOEvento;
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
        detalle.setIdDetalleSolicitud(nuevoId);
        almacen.getDetallesSolicitud().add(detalle);

        for (DTOEvento e : almacen.getEventos()) {
            if (e.getIdEvento() == detalle.getIdEvento()) {
                if (e.getDetallesSolicitud() == null) {
                    e.setDetallesSolicitud(new java.util.ArrayList<>());
                }
                e.getDetallesSolicitud().add(detalle);
                break; 
            }
        }

        return detalle;
    }
}
