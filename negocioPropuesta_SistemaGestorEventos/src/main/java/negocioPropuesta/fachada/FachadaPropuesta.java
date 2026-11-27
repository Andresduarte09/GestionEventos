package negocioPropuesta.fachada;

import negocioPropuesta.control.GestorPropuesta;
import negocioPropuesta.interfaces.ISubsistemaPropuesta;
import dto.*;

/**
 *
 * @author Andre
 */
public class FachadaPropuesta implements ISubsistemaPropuesta {

    private final GestorPropuesta gestor;

    public FachadaPropuesta(GestorPropuesta gestor) {
        this.gestor = gestor;
    }

    @Override
    public void seleccionarServicios(int idServicio, boolean seleccionado) {

        for (DTOServicio servicio : gestor.solicitarServicios()) {

            if (servicio.getIdServicio() == idServicio) {

                servicio.setSeleccionado(seleccionado);

                return;
            }
        }
    }

    @Override
    public boolean seleccionarEquipos(int idEquipo, int cantidad) {

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
    public boolean seleccionarPersonal(int idPersonal, int cantidad) {

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
    public boolean validarCantidadEquipo(int idEquipo, int cantidad) {

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

        gestor.validarHorario(horarioMontaje, horarioDesmontaje);

        if (!gestor.verificarDisponibilidadLocal(
                gestor.getIdEventoActual())) {

            throw new IllegalStateException(
                    "El local no está disponible.");
        }

        return gestor.guardarPropuestaActual(
                null,
                horarioMontaje,
                horarioDesmontaje,
                comentarios);
    }
}