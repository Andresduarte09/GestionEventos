package negocioPropuesta.fachada;

import com.mycompany.persistencia.AlmacenDatos;
import infraestructura.ProveedorExterno;
import negocioPropuesta.BO.ConfigPropuestaBO;
import negocioPropuesta.BO.PropuestaBO;
import negocioPropuesta.control.GestorPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;
import dto.*;
import java.util.List;

/**
 *
 * @author Andre
 */
public class FachadaPropuesta implements ISubsistemaPropuesta {

    private final GestorPropuesta gestor;
    private int idEventoActual;

    public FachadaPropuesta(
            AlmacenDatos almacen,
            ProveedorExterno proveedorExterno) {

        gestor = new GestorPropuesta(
                ConfigPropuestaBO.getInstance(
                        almacen, proveedorExterno),
                PropuestaBO.getInstance(
                        almacen, proveedorExterno)
        );
    }

    @Override
    public void seleccionarServicios(
            int idServicio,
            boolean seleccionado) {

        for (DTOServicio servicio : gestor.solicitarServicios()) {

            if (servicio.getIdServicio() == idServicio) {

                servicio.setSeleccionado(seleccionado);

                return;
            }
        }
    }

    @Override
    public boolean seleccionarEquipos(
            int idEquipo,
            int cantidad) {

        if (cantidad < 0) {
            return false;
        }

        for (DTOEquipo equipo : gestor.solicitarEquipos()) {

            if (equipo.getIdEquipo() == idEquipo) {

                if (cantidad > equipo.getDisponibilidad()) {
                    return false;
                }

                equipo.setCantidadSeleccionada(cantidad);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean seleccionarPersonal(
            int idPersonal,
            int cantidad) {

        if (cantidad < 0) {
            return false;
        }

        for (DTOPersonal personal : gestor.solicitarPersonal()) {

            if (personal.getIdEspecialidad() == idPersonal) {

                personal.setCantidad(cantidad);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verificarSeleccionServicios() {

        for (DTOServicio servicio : gestor.solicitarServicios()) {

            if (servicio.isSeleccionado()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verificarSeleccionEquipo() {

        for (DTOEquipo equipo : gestor.solicitarEquipos()) {

            if (equipo.getCantidadSeleccionada() > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean verificarSeleccionPersonal() {

        for (DTOPersonal personal : gestor.solicitarPersonal()) {

            if (personal.getCantidad() > 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean validarCantidadEquipo(
            int idEquipo,
            int cantidad) {

        if (cantidad <= 0) {
            return false;
        }

        for (DTOEquipo equipo : gestor.solicitarEquipos()) {

            if (equipo.getIdEquipo() == idEquipo) {

                return cantidad <= equipo.getDisponibilidad();
            }
        }

        return false;
    }

    @Override
    public DTOPropuesta enviarPropuestaHaciaCotizacion(
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios) {

        gestor.validarHorario(
                horarioMontaje,
                horarioDesmontaje);

        if (!gestor.verificarDisponibilidadLocal(idEventoActual)) {

            throw new IllegalStateException(
                    "El local no está disponible.");
        }

        return gestor.GuardarPropuestaActual(
                null,
                horarioMontaje,
                horarioDesmontaje,
                comentarios);
    }

    // Operaciones de integración con presentación y F4.

    @Override
    public List<DTOServicio> consultarServiciosDisponibles() {

        return gestor.consultarServiciosDisponibles();
    }

    @Override
    public List<DTOEquipo> obtenerEquiposDisponibles() {

        return gestor.obtenerEquiposDisponibles();
    }

    @Override
    public List<DTOPersonal> obtenerPersonalDisponible() {

        return gestor.obtenerPersonalDisponible();
    }

    @Override
    public List<DTOEquipo> consultarEquiposPropios() {

        return gestor.consultarEquiposPropios();
    }

    @Override
    public List<DTOEquipo> consultarEquiposExternos() {

        return gestor.consultarEquiposExternos();
    }

    @Override
    public List<DTOInventario> consultarInventariosDisponibles() {

        return gestor.consultarInventariosDisponibles();
    }

    @Override
    public List<DTOEquipo> buscarEquiposExternos(String texto) {

        return gestor.buscarEquiposExternos(texto);
    }

    @Override
    public DTOInventario obtenerResumenSeleccion(int idEvento) {

        DTOInventario resumen =
                gestor.obtenerResumenSeleccion(idEvento);

        idEventoActual = idEvento;

        return resumen;
    }

    @Override
    public DTOPropuesta consultarPropuesta(int idPropuesta) {

        return gestor.consultarPropuesta(idPropuesta);
    }

    @Override
    public DTOInventario consultarRecursosPropuesta(
            int idPropuesta) {

        return gestor.consultarRecursosPropuesta(idPropuesta);
    }

    @Override
    public DTOPropuesta actualizarPropuesta(DTOInventario cambios) {

        return gestor.actualizarPropuesta(cambios);
    }
}