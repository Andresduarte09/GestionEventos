package negocioPropuesta.interfaces;

import dto.DTODetalleSolicitud;
import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IConfigPropuestaBO {

    DTODetalleSolicitud guardarDetalleSolicitud(DTODetalleSolicitud detalle);

    List<DTODetalleSolicitud> consultarDetallesPorEvento(int idEvento);

    List<DTOServicio> consultarServicios();

    List<DTOEquipo> consultarEquipos();

    List<DTOPersonal> consultarPersonal();

    boolean verificarDisponibilidad(DTOInventario seleccion);

    // Operaciones de integración requeridas por el gestor.

    List<DTOEquipo> consultarEquiposPropios();

    List<DTOEquipo> consultarEquiposExternos();

    List<DTOInventario> consultarInventariosDisponibles();

    boolean verificarDisponibilidadLocal(int idEventoActual);
}