package negocioPropuesta.control;

import com.mycompany.persistencia.AlmacenDatos;
import subsistemaInfraestructura.interfaces.ISubsistemaInfraestructura;
import dto.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    private final AlmacenDatos almacen;
    private final ISubsistemaInfraestructura proveedorExterno;

    public GestorPropuesta(
            IConfigPropuestaBO configPropuestaBO,
            IPropuestaBO propuestaBO,
            AlmacenDatos almacen,
            ISubsistemaInfraestructura proveedorExterno) {

        this.almacen = almacen;
        this.proveedorExterno = proveedorExterno;
        this.configPropuestaBO = configPropuestaBO;
        this.propuestaBO = propuestaBO;
    }

    public int getIdEventoActual() {
        return idEventoActual;
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
        return configPropuestaBO.verificarDisponibilidad(seleccion);
    }

    public DTOInventario obtenerResumen(int idEvento) {

        if (idEvento <= 0) {

            throw new IllegalArgumentException(
                    "El identificador del evento debe ser positivo.");
        }

        seleccionActual = propuestaBO.consultarSeleccion(idEvento);
        idEventoActual = idEvento;

        return seleccionActual;
    }

    public DTOPropuesta guardarPropuestaActual(
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

    public DTOPropuesta actualizarPropuesta(DTOInventario cambios) {

        if (!verificarSeleccion(cambios)) {

            throw new IllegalArgumentException(
                    "Los recursos seleccionados no están disponibles.");
        }

        propuestaBO.actualizarSeleccion(cambios);

        seleccionActual = cambios;

        return crearPropuesta(null, null, null, "");
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

    public DTOInventario consultarRecursosPropuesta(int idPropuesta) {

        DTOPropuesta propuesta = propuestaBO.consultar(idPropuesta);

        if (propuesta == null) {
            return null;
        }

        return propuesta.getInventario();
    }

    public List<DTOEquipo> consultarEquiposPropios() {

        return new ArrayList<>(almacen.getEquipos());
    }

    public List<DTOEquipo> consultarEquiposExternos() {

        return new ArrayList<>(proveedorExterno.consultarEquipos());
    }

    public DTOInventario obtenerResumenSeleccion(int idEvento) {

        return obtenerResumen(idEvento);
    }

    public List<DTOInventario> consultarInventariosDisponibles() {

        return new ArrayList<>(almacen.getInventario());
    }

    public List<DTOEquipo> buscarEquiposExternos(String texto) {

        List<DTOEquipo> resultado = new ArrayList<>();

        String filtro = texto == null
                ? "" : texto.trim().toLowerCase();

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

    public boolean verificarDisponibilidadLocal(int idEventoActual) {
        DTOEvento actual = null;

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() == idEventoActual) {

                actual = evento;
                break;
            }
        }

        if (actual == null
                || actual.getIdLocalEvento() <= 0
                || actual.getFechaEvento() == null) {

            return false;
        }

        // Verificar las reservas existentes.
        for (DTOReserva reserva : almacen.getReservas()) {

            boolean mismoLocal =
                    reserva.getIdLocalEvento()
                    == actual.getIdLocalEvento();

            boolean mismaFecha = Objects.equals(
                    reserva.getFecha(),
                    actual.getFechaEvento());

            if (mismoLocal && mismaFecha) {

                return false;
            }
        }

        // Verificar otros eventos.
        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() != idEventoActual) {

                boolean mismaFecha = Objects.equals(
                        actual.getFechaEvento(),
                        evento.getFechaEvento());

                boolean mismoLocal =
                        actual.getIdLocalEvento()
                        == evento.getIdLocalEvento();

                boolean cancelado = "Cancelado"
                        .equalsIgnoreCase(evento.getEstadoAtencion());

                boolean rechazado = "Rechazada"
                        .equalsIgnoreCase(evento.getEstadoAtencion());

                if (mismaFecha && mismoLocal
                        && !cancelado && !rechazado) {

                    return false;
                }
            }
        }

        return true;
    }

    public void validarHorario(
            String horarioMontaje,
            String horarioDesmontaje) {

        // Validar campos vacíos.
        if (horarioMontaje == null || horarioMontaje.isBlank()
                || horarioDesmontaje == null
                || horarioDesmontaje.isBlank()) {

            throw new IllegalArgumentException(
                    "Debes ingresar ambos horarios.");
        }

        LocalTime montaje;
        LocalTime desmontaje;

        try {

            montaje = LocalTime.parse(horarioMontaje.trim());
            desmontaje = LocalTime.parse(horarioDesmontaje.trim());

        } catch (DateTimeParseException ex) {

            throw new IllegalArgumentException(
                    "Los horarios deben tener formato HH:mm.");
        }

        // Buscar el evento actual.
        DTOEvento eventoActual = null;

        for (DTOEvento evento : almacen.getEventos()) {

            if (evento.getIdEvento() == idEventoActual) {

                eventoActual = evento;
                break;
            }
        }

        if (eventoActual == null
                || eventoActual.getFechaEvento() == null
                || eventoActual.getHoraInicio() == null
                || eventoActual.getHoraFin() == null) {

            throw new IllegalStateException(
                    "No se encontraron los horarios del evento.");
        }

        int maxHorasMontaje = 0;
        int maxHorasDesmontaje = 0;

        // Obtener las horas máximas del personal seleccionado.
        for (DTOPersonal personal : capturarSeleccionActual().getPersonal()) {

            if (personal.getCantidad() > 0) {

                maxHorasMontaje = Math.max(
                        maxHorasMontaje,
                        personal.getHorasMontaje());

                maxHorasDesmontaje = Math.max(
                        maxHorasDesmontaje,
                        personal.getHorasDesmontaje());
            }
        }

        // Establecer fecha y hora del evento.
        LocalDateTime inicioEvento = LocalDateTime.of(
                eventoActual.getFechaEvento(),
                eventoActual.getHoraInicio());

        LocalDateTime finEvento = LocalDateTime.of(
                eventoActual.getFechaEvento(),
                eventoActual.getHoraFin());

        // Si el evento termina después de medianoche.
        if (finEvento.isBefore(inicioEvento)) {

            finEvento = finEvento.plusDays(1);
        }

        // Establecer fecha y hora del montaje.
        LocalDateTime fechaMontaje = LocalDateTime.of(
                inicioEvento.toLocalDate(),
                montaje);

        // Establecer fecha y hora del desmontaje.
        LocalDateTime fechaDesmontaje = LocalDateTime.of(
                finEvento.toLocalDate(),
                desmontaje);

        // Calcular los horarios límite.
        LocalDateTime horaMaximaMontaje
                = inicioEvento.minusHours(maxHorasMontaje);

        LocalDateTime horaMinimaDesmontaje
                = finEvento.plusHours(maxHorasDesmontaje);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm (dd/MM/yyyy)");

        // Validar montaje.
        if (fechaMontaje.isAfter(horaMaximaMontaje)) {

            throw new IllegalArgumentException(
                    "El montaje debe comenzar como máximo a las "
                    + horaMaximaMontaje.format(formato)
                    + ".\nHoras requeridas: " + maxHorasMontaje);
        }

        // Validar desmontaje.
        if (fechaDesmontaje.isBefore(horaMinimaDesmontaje)) {

            throw new IllegalArgumentException(
                    "El desmontaje debe finalizar como mínimo a las "
                    + horaMinimaDesmontaje.format(formato)
                    + ".\nHoras requeridas: " + maxHorasDesmontaje);
        }
    }
}
