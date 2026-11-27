package subsistemaInfraestructura.interfaces;
import dto.DTOEquipo;
import java.util.List;
import subsistemaInfraestructura.dto.DTOEquipoProveedor;
import subsistemaInfraestructura.dto.DTORecursoProveedor;

public interface ISubsistemaInfraestructura {
    // Adaptación al DTO mutable que ya usa el subsistema de propuesta.
    List<DTOEquipo> consultarEquipos();
    List<DTOEquipoProveedor> consultarCatalogoEquipos();
    DTOEquipoProveedor consultarEquipo(int idEquipo);
    List<DTOEquipoProveedor> buscarEquipos(String nombre);
    List<DTORecursoProveedor> consultarRecursos();
    DTORecursoProveedor consultarRecurso(int idRecurso);
    DTORecursoProveedor consultarRecursoPorNombre(String nombre);
    float consultarTarifaEquipo(int idEquipo);
    boolean verificarDisponibilidad(int idEquipo, int cantidad);
}
