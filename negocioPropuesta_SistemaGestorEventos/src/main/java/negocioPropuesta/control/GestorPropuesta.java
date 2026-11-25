package negocioPropuesta.control;

import dto.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import negocioPropuesta.interfaces.IConfigPropuestaBO;
import negocioPropuesta.interfaces.IPropuestaBO;

/**
 *
 * @author Andre
 */
public class GestorPropuesta {

    private DTOInventario seleccionActual;
    private int idEventoActual;

    private final IConfigPropuestaBO configPropuestaBO;
    private final IPropuestaBO propuestaBO;

    public GestorPropuesta(
            IConfigPropuestaBO configPropuestaBO,
            IPropuestaBO propuestaBO) {

        this.configPropuestaBO = configPropuestaBO;
        this.propuestaBO = propuestaBO;
    }

    public List<DTOServicio> solicitarServicios() {

        return configPropuestaBO.consultarServicios();
    }

    public List<DTOEquipo> solicitarEquipos() {

        return configPropuestaBO.consultarEquipos();
    }

    public List<DTOPersonal> solicitarPersonal() {

        return configPropuestaBO.consultarPersonal();
    }

    public boolean verificarSeleccion(DTOInventario seleccion) {

        return configPropuestaBO.verificarDisponibilidad(
                seleccion);
    }

    public DTOInventario obtenerResumen(int idEvento) {

        if (idEvento <= 0) {

            throw new IllegalArgumentException(
                    "El identificador del evento debe ser positivo.");
        }

        seleccionActual =
                propuestaBO.consultarSeleccion(idEvento);

        idEventoActual = idEvento;

        return seleccionActual;
    }

    public DTOPropuesta GuardarPropuestaActual(
            String folio,
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios) {

        DTOPropuesta propuesta = crearPropuesta(
                folio,
                horarioMontaje,
                horarioDesmontaje,
                comentarios);

        return propuestaBO.guardar(propuesta);
    }

    public DTOPropuesta actualizarPropuesta(
            DTOInventario cambios) {

        if (!verificarSeleccion(cambios)) {

            throw new IllegalArgumentException(
                    "Los recursos seleccionados no están disponibles.");
        }

        propuestaBO.actualizarSeleccion(cambios);

        seleccionActual = cambios;

        return crearPropuesta(
                null,
                null,
                null,
                "");
    }

    public List<DTOServicio> consultarServiciosDisponibles() {

        return solicitarServicios();
    }

    public List<DTOEquipo> obtenerEquiposDisponibles() {

        return solicitarEquipos();
    }

    public List<DTOPersonal> obtenerPersonalDisponible() {

        return solicitarPersonal();
    }

    public DTOInventario capturarSeleccionActual() {

        DTOInventario seleccion = new DTOInventario();

        for (DTOServicio servicio : solicitarServicios()) {

            if (servicio.isSeleccionado()) {

                seleccion.getServicios().add(servicio);
            }
        }

        for (DTOEquipo equipo : solicitarEquipos()) {

            if (equipo.getCantidadSeleccionada() > 0) {

                seleccion.getEquipos().add(equipo);
            }
        }

        for (DTOPersonal personal : solicitarPersonal()) {

            if (personal.getCantidad() > 0) {

                seleccion.getPersonal().add(personal);
            }
        }

        return seleccion;
    }

    public DTOPropuesta consultarPropuesta(int idPropuesta) {

        return propuestaBO.consultar(idPropuesta);
    }

    public DTOInventario consultarRecursosPropuesta(
            int idPropuesta) {

        DTOPropuesta propuesta =
                propuestaBO.consultar(idPropuesta);

        if (propuesta == null) {
            return null;
        }

        return propuesta.getInventario();
    }

    public List<DTOEquipo> consultarEquiposPropios() {

        return configPropuestaBO.consultarEquiposPropios();
    }

    public List<DTOEquipo> consultarEquiposExternos() {

        return configPropuestaBO.consultarEquiposExternos();
    }

    public DTOInventario obtenerResumenSeleccion(int idEvento) {

        return obtenerResumen(idEvento);
    }

    public List<DTOInventario> consultarInventariosDisponibles() {

        return configPropuestaBO.consultarInventariosDisponibles();
    }

    public List<DTOEquipo> buscarEquiposExternos(String texto) {

        List<DTOEquipo> resultado = new ArrayList<>();

        String filtro = texto == null
                ? ""
                : texto.trim().toLowerCase();

        for (DTOEquipo equipo : consultarEquiposExternos()) {

            if (equipo.getNombre() != null
                    && equipo.getNombre().toLowerCase()
                            .contains(filtro)) {

                resultado.add(equipo);
            }
        }

        return resultado;
    }

    public DTOPropuesta crearPropuesta(
            String folio,
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios) {

        if (idEventoActual <= 0) {

            throw new IllegalStateException(
                    "No se ha establecido un evento.");
        }

        seleccionActual = capturarSeleccionActual();

        if (!verificarSeleccion(seleccionActual)) {

            throw new IllegalStateException(
                    "Los recursos seleccionados no están disponibles.");
        }

        DTOPropuesta propuesta = new DTOPropuesta();

        propuesta.setIdEvento(idEventoActual);
        propuesta.setFolio(folio);
        propuesta.setHorarioMontaje(horarioMontaje);
        propuesta.setHorarioDesmontaje(horarioDesmontaje);

        propuesta.setComentarios(
                comentarios == null ? "" : comentarios.trim());

        propuesta.setInventario(seleccionActual);

        return propuesta;
    }

    public boolean verificarDisponibilidadLocal(
            int idEventoActual) {

        return configPropuestaBO.verificarDisponibilidadLocal(
                idEventoActual);
    }

    // Operación del diagrama de secuencia.

    public void validarHorario(
            String horarioMontaje,
            String horarioDesmontaje) {

        try {

            if (horarioMontaje != null
                    && !horarioMontaje.isBlank()) {

                LocalTime.parse(horarioMontaje.trim());
            }

            if (horarioDesmontaje != null
                    && !horarioDesmontaje.isBlank()) {

                LocalTime.parse(horarioDesmontaje.trim());
            }

        } catch (DateTimeParseException ex) {

            throw new IllegalArgumentException(
                    "Los horarios deben tener formato HH:mm.");
        }
    }
}