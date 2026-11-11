package negocioPropuesta.control;

import dto.DTOEquipo;
import dto.DTOInventario;
import dto.DTOPersonal;
import dto.DTOPropuesta;
import dto.DTOServicio;
import java.util.List;
import negocioPropuesta.interfaces.IConfigPropuestaBO;
import negocioPropuesta.interfaces.IPropuestaBO;

/**
 * Gestor del subsistema de propuesta.
 */
public class GestorPropuesta {

    private DTOInventario seleccionActual;
    private final IConfigPropuestaBO configPropuestaBO;
    private final IPropuestaBO propuestaBO;

    public GestorPropuesta(IConfigPropuestaBO configPropuestaBO, IPropuestaBO propuestaBO) {
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
        return configPropuestaBO.verificarDisponibilidad(seleccion);
    }

    public DTOInventario obtenerResumen(int idEvento) {
        seleccionActual = propuestaBO.consultarSeleccion(idEvento);
        return seleccionActual;
    }

    //crea la propuesta y la guarda; si no llega folio se genera uno con el id
    public DTOPropuesta solicitarPropuesta(String folio, String horarioMontaje, String horarioDesmontaje, String comentarios) {
        DTOPropuesta propuesta = new DTOPropuesta();
        propuesta.setFolio(folio);
        propuesta.setHorarioMontaje(horarioMontaje);
        propuesta.setHorarioDesmontaje(horarioDesmontaje);
        propuesta.setComentarios(comentarios == null ? "" : comentarios.trim());

        DTOPropuesta guardada = propuestaBO.guardar(propuesta);
        if (guardada.getFolio() == null || guardada.getFolio().trim().isEmpty()) {
            guardada.setFolio("PROP-" + guardada.getIdPropuesta());
        }
        return guardada;
    }

    public void actualizarPropuesta(DTOInventario cambios) {
        propuestaBO.actualizarSeleccion(cambios);
        seleccionActual = cambios;
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