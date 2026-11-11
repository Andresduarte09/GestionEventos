package negocioPropuesta.interfaces;

import dto.DTOEquipo;
import dto.DTOPersonal;
import dto.DTOServicio;
import java.util.List;

public interface IConfigPropuestaBO {

    List<DTOServicio> consultarServicios();

    List<DTOEquipo> consultarEquipos();

    List<DTOPersonal> consultarPersonal();

    void seleccionarServicio(int idServicio, boolean seleccionado);

    void seleccionarEquipo(int idEquipo, int cantidad);

    void seleccionarPersonal(int idEspecialidad, int cantidad);

    boolean haySeleccionEquipos();

    boolean haySeleccionPersonal();
}
