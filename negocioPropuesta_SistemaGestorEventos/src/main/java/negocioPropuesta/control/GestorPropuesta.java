package negocioPropuesta.control;

import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOPropuesta;
import dto.DTOServicio;
import java.util.List;
import negocioPropuesta.interfaces.IConfigPropuestaBO;
import negocioPropuesta.interfaces.IPropuestaBO;

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

    public void establecerEventoActual(int idEvento) {
        if (idEvento <= 0) {
            throw new IllegalArgumentException(
                    "El ID del evento debe ser positivo.");
        }

        this.idEventoActual = idEvento;
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

    private DTOInventario capturarSeleccionActual() {

        DTOInventario seleccion = new DTOInventario();

        for (DTOServicio s : solicitarServicios()) {
            if (s.isSeleccionado()) {
                seleccion.getServicios().add(s);
            }
        }

        for (DTOEquipo e : solicitarEquipos()) {
            if (e.getCantidadSeleccionada() > 0) {
                seleccion.getEquipos().add(e);
            }
        }

        for (DTOPersonal p : solicitarPersonal()) {
            if (p.getCantidad() > 0) {
                seleccion.getPersonal().add(p);
            }
        }

        return seleccion;
    }

    public DTOInventario obtenerResumen(int idEvento) {

        establecerEventoActual(idEvento);

        propuestaBO.registrarSeleccionEvento(
                idEvento,
                capturarSeleccionActual()
        );

        seleccionActual =
                propuestaBO.consultarSeleccion(idEvento);

        return seleccionActual;
    }

    public DTOPropuesta solicitarPropuesta(
            String folio,
            String horarioMontaje,
            String horarioDesmontaje,
            String comentarios) {

        if (idEventoActual <= 0) {
            throw new IllegalStateException(
                    "Primero debes establecer el evento actual.");
        }

        propuestaBO.registrarSeleccionEvento(
                idEventoActual,
                capturarSeleccionActual()
        );

        DTOInventario inventario =
                propuestaBO.consultarSeleccion(idEventoActual);

        DTOPropuesta propuesta = new DTOPropuesta();

        propuesta.setIdEvento(idEventoActual);
        propuesta.setInventario(inventario);
        propuesta.setFolio(folio);
        propuesta.setHorarioMontaje(horarioMontaje);
        propuesta.setHorarioDesmontaje(horarioDesmontaje);
        propuesta.setComentarios(
                comentarios == null ? "" : comentarios.trim()
        );

        DTOPropuesta guardada = propuestaBO.guardar(propuesta);

        if (guardada.getFolio() == null
                || guardada.getFolio().trim().isEmpty()) {

            guardada.setFolio(
                    "PROP-" + guardada.getIdPropuesta()
            );

            guardada = propuestaBO.guardar(guardada);
        }

        return guardada;
    }

    public DTOPropuesta consultarPropuesta(int idPropuesta) {
        return propuestaBO.consultar(idPropuesta);
    }

    public DTOInventario consultarRecursosPropuesta(int idPropuesta) {
        return propuestaBO.consultarRecursosPropuesta(idPropuesta);
    }

    public void actualizarPropuesta(DTOInventario cambios) {

        propuestaBO.actualizarSeleccion(cambios);

        if (idEventoActual > 0) {

            propuestaBO.registrarSeleccionEvento(
                    idEventoActual,
                    capturarSeleccionActual()
            );

            seleccionActual =
                    propuestaBO.consultarSeleccion(idEventoActual);

        } else {
            seleccionActual = cambios;
        }
    }

    public List<DTOServicio> consultarServiciosDisponibles() {
        return configPropuestaBO.consultarServicios();
    }

    public List<DTOEquipo> obtenerEquiposDisponibles() {
        return configPropuestaBO.consultarEquipos();
    }

    public List<DTOPersonal> obtenerPersonalDisponible() {
        return configPropuestaBO.consultarPersonal();
    }
}